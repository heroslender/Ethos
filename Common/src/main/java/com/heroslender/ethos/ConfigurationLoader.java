package com.heroslender.ethos;

import com.heroslender.ethos.adapter.exceptions.AdapterNotFoundException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

@SuppressWarnings("unused")
public abstract class ConfigurationLoader<T, C> {
    private final Logger logger = Logger.getLogger(getClass().getSimpleName());
    private final C config;
    private final Runnable saveConfig;
    private final Class<T> clazz;

    protected ConfigurationLoader(@NotNull C config, Runnable saveConfig, @NotNull Class<T> clazz) {
        this.config = config;
        this.saveConfig = saveConfig;
        this.clazz = clazz;
    }

    public void save(@NotNull Object instance) {
        for (Field field : clazz.getDeclaredFields()) {
            try {
                saveField(field, instance);
            } catch (IllegalAccessException e) {
                logger.log(Level.SEVERE, e, () -> "Failed to initialize the field " + field.getName());
            }
        }

        if (getSaveConfig() != null) {
            getSaveConfig().run();
        }
    }

    public T load() {
        final T instance = initClass(clazz);
        if (instance == null) {
            return null;
        }

        for (Field field : clazz.getDeclaredFields()) {
            try {
                loadField(field, instance);
            } catch (IllegalAccessException | AdapterNotFoundException e) {
                logger.log(Level.SEVERE, e, () -> "Failed to initialize the field " + field.getName());
            }
        }

        return instance;
    }

    protected void saveField(@NotNull final Field field, @NotNull final Object instance) throws IllegalAccessException {
        field.setAccessible(true);

        final String configFieldName = field.getName();

        try {
            setConfigValue(field, configFieldName, field.get(instance));
        } catch (AdapterNotFoundException e) {
            logger.log(Level.SEVERE, e, () -> "Failed to initialize the field " + field.getName());
        }
    }

    protected void loadField(@NotNull final Field field, @NotNull final Object instance) throws IllegalAccessException, AdapterNotFoundException {
        field.setAccessible(true);

        final String configFieldName = field.getName();

        final Object configValue = getConfigValue(field, configFieldName, field.get(instance));
        if (configValue != null) {
            field.set(instance, configValue);
        }
    }

    public abstract Object getConfigValue(Field field, String valuePath, @Nullable Object defaultValue) throws AdapterNotFoundException;

    public abstract void setConfigValue(Field field, String valuePath, @Nullable Object value) throws AdapterNotFoundException;

    @Nullable
    protected T initClass(@NotNull Class<T> clazz) {
        Objects.requireNonNull(clazz, "clazz is null");

        T instance = null;
        try {
            final Constructor<T> constructor = clazz.getConstructor();
            instance = constructor.newInstance();
        } catch (NoSuchMethodException e) {
            getLogger().log(Level.SEVERE, e, () -> "Couldn't find the default constructor for the class " + clazz.getSimpleName());
        } catch (IllegalAccessException | InstantiationException e) {
            getLogger().log(Level.SEVERE, e, () -> "Failed to initialize the class " + clazz.getSimpleName());
        } catch (InvocationTargetException e) {
            getLogger().log(Level.SEVERE, e.getCause(), () -> "An exception was thrown when trying to initialize the class " + clazz.getSimpleName());
        }

        return instance;
    }

    public Logger getLogger() {
        return logger;
    }

    public C getConfig() {
        return config;
    }

    public Runnable getSaveConfig() {
        return saveConfig;
    }

    public Class<T> getClazz() {
        return clazz;
    }
}
