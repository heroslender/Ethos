package com.heroslender.ethos.bukkit.adapter;

import com.heroslender.ethos.adapter.TypeAdapter;
import org.bukkit.configuration.ConfigurationSection;

import java.lang.reflect.Type;

public interface BukkitTypeAdapter<T> extends TypeAdapter<T> {

    T get(ConfigurationSection configuration, String path);

    default T get(ConfigurationSection configurationSection, String path, Type type) {
        return get(configurationSection, path);
    }

    default void save(ConfigurationSection configuration, String path, Object defaultValue) {
        configuration.set(path, defaultValue);
    }

    default void save(ConfigurationSection configuration, String path, Object defaultValue, Type type) {
        save(configuration, path, defaultValue);
    }

    void saveDefault(ConfigurationSection configuration, String path, Object defaultValue);

    default void saveDefault(ConfigurationSection configuration, String path, Object defaultValue, Type type) {
        saveDefault(configuration, path, defaultValue);
    }
}
