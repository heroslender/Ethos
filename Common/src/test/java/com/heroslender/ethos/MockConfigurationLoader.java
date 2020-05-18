package com.heroslender.ethos;

import com.heroslender.ethos.adapter.exceptions.AdapterNotFoundException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MockConfigurationLoader<C> extends ConfigurationLoader<C, Object> {
    public MockConfigurationLoader(@NotNull Object config, Runnable saveConfig, @NotNull Class<C> clazz) {
        super(config, saveConfig, clazz);
    }

    @Override
    public Object getConfigValue(AnnotatedField field, @Nullable Object defaultValue) throws AdapterNotFoundException {
        return null;
    }

    @Override
    public void setConfigValue(AnnotatedField field, @Nullable Object value) throws AdapterNotFoundException {

    }
}
