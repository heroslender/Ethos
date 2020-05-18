package com.heroslender.ethos.bukkit.adapter.types;

import com.heroslender.ethos.AnnotatedField;
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
    public Object get(ConfigurationSection configurationSection, AnnotatedField field) {
        return BukkitConfigurationLoader.load(
                configurationSection.getConfigurationSection(field.getSerializedName()),
                null,
                field.getType()
        );
    }

    @Override
    public void save(ConfigurationSection configuration, AnnotatedField field, Object defaultValue) {
        ConfigurationSection section = configuration.createSection(field.getSerializedName());

        new BukkitConfigurationLoader(section, null, field.getType()).save(defaultValue);
    }

    @Override
    public void saveDefault(ConfigurationSection configuration, AnnotatedField field, Object defaultValue) {
        configuration.createSection(field.getSerializedName());

        if (defaultValue == null) {
            BukkitConfigurationLoader.load(
                    configuration.getConfigurationSection(field.getSerializedName()),
                    null,
                    field.getType()
            );
        } else {
            save(configuration, field, defaultValue);
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
