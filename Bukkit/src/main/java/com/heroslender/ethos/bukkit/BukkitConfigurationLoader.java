package com.heroslender.ethos.bukkit;

import com.heroslender.ethos.bukkit.adapter.BukkitTypeAdapter;
import com.heroslender.ethos.bukkit.adapter.BukkitTypeAdapterFactory;
import com.heroslender.ethos.ConfigurationLoader;
import com.heroslender.ethos.adapter.exceptions.AdapterNotFoundException;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.util.Objects;
import java.util.logging.Level;

@SuppressWarnings("unused")
public class BukkitConfigurationLoader<T> extends ConfigurationLoader<T, ConfigurationSection> {
    public static final BukkitTypeAdapterFactory TYPE_ADAPTER_FACTORY = BukkitTypeAdapterFactory.getInstance();

    public BukkitConfigurationLoader(@NotNull ConfigurationSection config, Runnable saveConfig, @NotNull Class<T> clazz) {
        super(config, saveConfig, clazz);
    }

    @Nullable
    public static <T> T load(@NotNull Plugin plugin, @NotNull Class<T> clazz) {
        Objects.requireNonNull(plugin, "plugin is null");

        return load(plugin, plugin.getConfig(), clazz);
    }

    @Nullable
    public static <T> T load(@NotNull Plugin plugin, @NotNull String section, @NotNull Class<T> clazz) {
        Objects.requireNonNull(plugin, "plugin is null");
        Objects.requireNonNull(section, "section is null");

        ConfigurationSection configurationSection = plugin.getConfig().getConfigurationSection(section);
        if (configurationSection == null) {
            plugin.getLogger().log(Level.WARNING, "The configuration section {0} does not exist, creating an empty one", section);
            configurationSection = plugin.getConfig().createSection(section);
        }

        return load(plugin, configurationSection, clazz);
    }

    @Nullable
    public static <T> T load(@NotNull Plugin plugin, @NotNull ConfigurationSection config, @NotNull Class<T> clazz) {
        Objects.requireNonNull(plugin, "plugin is null");

        return load(config, plugin::saveConfig, clazz);
    }

    @Nullable
    public static <T> T load(@NotNull ConfigurationSection config, Runnable saveConfig, @NotNull Class<T> clazz) {
        Objects.requireNonNull(config, "config is null");
        Objects.requireNonNull(clazz, "clazz is null");

        final ConfigurationLoader<T, ConfigurationSection> loader = new com.heroslender.ethos.bukkit.BukkitConfigurationLoader<>(config, saveConfig, clazz);
        return loader.load();
    }

    @Override
    public Object getConfigValue(Field field, String valuePath, @Nullable Object defaultValue) throws AdapterNotFoundException {
        final BukkitTypeAdapter<?> typeAdapter = TYPE_ADAPTER_FACTORY.getTypeAdapter(field.getType());

        if (!getConfig().isSet(valuePath)) {
            getLogger().log(Level.INFO, "The field {0} is not present in the config, creating it.", valuePath);
            typeAdapter.saveDefault(getConfig(), valuePath, defaultValue, field.getGenericType());
            if (getSaveConfig() != null) {
                getSaveConfig().run();
            }
        }

        return typeAdapter.get(getConfig(), valuePath, field.getGenericType());
    }

    @Override
    public void setConfigValue(Field field, String valuePath, @Nullable Object value) throws AdapterNotFoundException {
        final BukkitTypeAdapter<?> typeAdapter = TYPE_ADAPTER_FACTORY.getTypeAdapter(field.getType());

        typeAdapter.save(getConfig(), valuePath, value, field.getGenericType());
    }
}
