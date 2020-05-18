package com.heroslender.ethos.bukkit.adapter.types;

import com.heroslender.ethos.AnnotatedField;
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
    public Map get(ConfigurationSection configurationSection, AnnotatedField field) {
        if (!(field.getGenericType() instanceof ParameterizedType)) {
            throw new IllegalArgumentException("Map without types?!?");
        }

        ParameterizedType mapType = (ParameterizedType) field.getGenericType();
        Class<?> keyType = (Class<?>) mapType.getActualTypeArguments()[0];
        Type valType = mapType.getActualTypeArguments()[1];
        Class<?> valueType = (Class<?>) valType;

        BukkitTypeAdapter<?> keyAdapter = BukkitConfigurationLoader.TYPE_ADAPTER_FACTORY.getTypeAdapter(keyType);
        BukkitTypeAdapter<?> valueAdapter = BukkitConfigurationLoader.TYPE_ADAPTER_FACTORY.getTypeAdapter(valueType);

        ConfigurationSection section = configurationSection.getConfigurationSection(field.getSerializedName());
        if (section == null) {
            return null;
        }

        Map map = new HashMap();
        for (String key : section.getKeys(false)) {
            AnnotatedField valField = new AnnotatedField(valType, key);

            map.put(keyAdapter.from(key), valueAdapter.get(section, valField));
        }

        return map;
    }

    @Override
    public void save(ConfigurationSection configuration, AnnotatedField field, Object defaultValue) {
        save(configuration, field.getSerializedName(), defaultValue, field, true);
    }

    @Override
    public void saveDefault(ConfigurationSection configuration, AnnotatedField field, Object defaultValue) {
        save(configuration, field.getSerializedName(), defaultValue, field, false);
    }

    private void save(ConfigurationSection configuration, String path, Object defaultValue, AnnotatedField field, boolean forced) {
        if (!(field.getGenericType() instanceof ParameterizedType)) {
            throw new IllegalArgumentException("Map without types?!?");
        }

        if (defaultValue == null) {
            defaultValue = Collections.emptyMap();
        }

        Type valType = ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[1];
        Class<?> valueType = (Class<?>) valType;
        BukkitTypeAdapter<?> valueAdapter = BukkitConfigurationLoader.TYPE_ADAPTER_FACTORY.getTypeAdapter(valueType);

        ConfigurationSection section = configuration.createSection(path);
        Map map = (Map) defaultValue;
        for (Object o : map.entrySet()) {
            Map.Entry entry = (Map.Entry) o;
            AnnotatedField valField = new AnnotatedField(valType, entry.getKey().toString());

            if (forced) {
                valueAdapter.save(section, valField,  entry.getValue());
            } else {
                valueAdapter.saveDefault(section, valField, entry.getValue());
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
