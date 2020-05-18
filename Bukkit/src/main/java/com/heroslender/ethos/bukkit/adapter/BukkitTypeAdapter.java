package com.heroslender.ethos.bukkit.adapter;

import com.heroslender.ethos.AnnotatedField;
import com.heroslender.ethos.adapter.TypeAdapter;
import org.bukkit.configuration.ConfigurationSection;

import java.lang.reflect.Type;

public interface BukkitTypeAdapter<T> extends TypeAdapter<T> {

    T get(ConfigurationSection configuration, String path);

    default T get(ConfigurationSection configurationSection, AnnotatedField field) {
        return get(configurationSection, field.getSerializedName());
    }

    default void save(ConfigurationSection configuration, String path, Object defaultValue) {
        configuration.set(path, defaultValue);
    }

    default void save(ConfigurationSection configuration, AnnotatedField field, Object defaultValue) {
        save(configuration, field.getSerializedName(), defaultValue);
    }

    void saveDefault(ConfigurationSection configuration, String path, Object defaultValue);

    default void saveDefault(ConfigurationSection configuration, AnnotatedField field, Object defaultValue) {
        saveDefault(configuration, field.getSerializedName(), defaultValue);
    }
}
