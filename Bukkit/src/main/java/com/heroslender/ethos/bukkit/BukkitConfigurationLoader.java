package com.heroslender.ethos.bukkit;

import com.heroslender.ethos.ConfigurationLoader;
import com.heroslender.ethos.AnnotatedField;
import com.heroslender.ethos.adapter.exceptions.AdapterNotFoundException;
import com.heroslender.ethos.bukkit.adapter.BukkitTypeAdapter;
import com.heroslender.ethos.bukkit.adapter.BukkitTypeAdapterFactory;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
    public Object getConfigValue(AnnotatedField field, @Nullable Object defaultValue) throws AdapterNotFoundException {
        final BukkitTypeAdapter<?> typeAdapter = TYPE_ADAPTER_FACTORY.getTypeAdapter(field.getType());
        final String path = field.getSerializedName();

        if (!field.isOptional() && !getConfig().isSet(path)) {
            getLogger().log(Level.INFO, "The field {0} is not present in the config, creating it.", path);
            typeAdapter.saveDefault(getConfig(), field, defaultValue);
            if (getSaveConfig() != null) {
                getSaveConfig().run();
            }
        }

        return typeAdapter.get(getConfig(), field);
    }

    @Override
    public void setConfigValue(AnnotatedField field, @Nullable Object value) throws AdapterNotFoundException {
        final BukkitTypeAdapter<?> typeAdapter = TYPE_ADAPTER_FACTORY.getTypeAdapter(field.getType());

        typeAdapter.save(getConfig(), field, value);
    }
}
