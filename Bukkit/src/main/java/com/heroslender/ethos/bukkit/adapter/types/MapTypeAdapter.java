package com.heroslender.ethos.bukkit.adapter.types;

import com.heroslender.ethos.bukkit.BukkitConfigurationLoader;
import com.heroslender.ethos.bukkit.adapter.BukkitTypeAdapter;
import org.bukkit.configuration.ConfigurationSection;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("rawtypes")
public class MapTypeAdapter implements BukkitTypeAdapter<Map> {
    public static final MapTypeAdapter INSTANCE = new MapTypeAdapter();
    private static final Class<Map> CLAZZ = Map.class;

    private MapTypeAdapter() {
    }

    @Override
    public Class<Map> getType() {
        return CLAZZ;
    }

    @Override
    public Map from(String value) {
        return Collections.singletonMap(value, value);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map get(ConfigurationSection configurationSection, String path, Type type) {
        if (!(type instanceof ParameterizedType)) {
            throw new IllegalArgumentException("Map without types?!?");
        }

        ParameterizedType mapType = (ParameterizedType) type;
        Class<?> keyType = (Class<?>) mapType.getActualTypeArguments()[0];
        Type valType = mapType.getActualTypeArguments()[1];
        Class<?> valueType = (Class<?>) valType;

        BukkitTypeAdapter<?> keyAdapter = BukkitConfigurationLoader.TYPE_ADAPTER_FACTORY.getTypeAdapter(keyType);
        BukkitTypeAdapter<?> valueAdapter = BukkitConfigurationLoader.TYPE_ADAPTER_FACTORY.getTypeAdapter(valueType);

        ConfigurationSection section = configurationSection.getConfigurationSection(path);
        Map map = new HashMap();
        for (String key : section.getKeys(false)) {
            map.put(keyAdapter.from(key), valueAdapter.get(section, key, valType));
        }

        return map;
    }

    @Override
    public void save(ConfigurationSection configuration, String path, Object defaultValue, Type type) {
        save(configuration, path, defaultValue, type, true);
    }

    @Override
    public void saveDefault(ConfigurationSection configuration, String path, Object defaultValue, Type type) {
        save(configuration, path, defaultValue, type, false);
    }

    private void save(ConfigurationSection configuration, String path, Object defaultValue, Type type, boolean forced) {
        if (!(type instanceof ParameterizedType)) {
            throw new IllegalArgumentException("Map without types?!?");
        }

        if (defaultValue == null) {
            defaultValue = Collections.emptyMap();
        }

        Type valType = ((ParameterizedType) type).getActualTypeArguments()[1];
        Class<?> valueType = (Class<?>) valType;
        BukkitTypeAdapter<?> valueAdapter = BukkitConfigurationLoader.TYPE_ADAPTER_FACTORY.getTypeAdapter(valueType);

        ConfigurationSection section = configuration.createSection(path);
        Map map = (Map) defaultValue;
        for (Object o : map.entrySet()) {
            Map.Entry entry = (Map.Entry) o;

            if (forced) {
                valueAdapter.save(section, entry.getKey().toString(), entry.getValue(), valType);
            } else {
                valueAdapter.saveDefault(section, entry.getKey().toString(), entry.getValue(), valType);
            }
        }
    }

    @Override
    public Map get(ConfigurationSection configuration, String path) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void saveDefault(ConfigurationSection configuration, String path, Object defaultValue) {
        throw new UnsupportedOperationException("Not implemented");
    }
}
