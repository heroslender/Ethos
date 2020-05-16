package com.heroslender.ethos.bukkit.adapter.types;

import com.heroslender.ethos.bukkit.BukkitConfigurationLoader;
import com.heroslender.ethos.bukkit.adapter.BukkitTypeAdapter;
import org.bukkit.configuration.ConfigurationSection;

import java.lang.reflect.Type;
import java.util.Collections;

@SuppressWarnings("rawtypes")
public class ObjectTypeAdapter implements BukkitTypeAdapter<Object> {
    public static final ObjectTypeAdapter INSTANCE = new ObjectTypeAdapter();
    private static final Class<Object> CLAZZ = Object.class;

    private ObjectTypeAdapter() {
    }

    @Override
    public Class<Object> getType() {
        return CLAZZ;
    }

    @Override
    public Object from(String value) {
        return Collections.singletonList(value);
    }

    @Override
    public Object get(ConfigurationSection configurationSection, String path, Type type) {
        return BukkitConfigurationLoader.load(
                configurationSection.getConfigurationSection(path),
                null,
                (Class<?>) type
        );
    }

    @Override
    public void save(ConfigurationSection configuration, String path, Object defaultValue, Type type) {
        ConfigurationSection section = configuration.createSection(path);

        new BukkitConfigurationLoader(section, null, (Class<?>) type).save(defaultValue);
    }

    @Override
    public void saveDefault(ConfigurationSection configuration, String path, Object defaultValue, Type type) {
        configuration.createSection(path);

        if (defaultValue == null) {
            BukkitConfigurationLoader.load(
                    configuration.getConfigurationSection(path),
                    null,
                    (Class<?>) type
            );
        } else {
            save(configuration, path, defaultValue, type);
        }
    }

    @Override
    public Object get(ConfigurationSection configuration, String path) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void saveDefault(ConfigurationSection configuration, String path, Object defaultValue) {
        throw new UnsupportedOperationException("Not implemented");
    }
}
