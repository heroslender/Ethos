package com.heroslender.ethos.bukkit.adapter.types;

import com.google.common.collect.Lists;
import com.heroslender.ethos.adapter.types.*;
import com.heroslender.ethos.bukkit.adapter.BukkitTypeAdapter;
import org.bukkit.configuration.ConfigurationSection;

import java.util.List;

public class PrimitiveTypeAdapters {
    public static final List<BukkitTypeAdapter<?>> PRIMITIVE_ADAPTERS = Lists.newArrayList(
            IntegerAdapter.INSTANCE,
            LongAdapter.INSTANCE,
            DoubleAdapter.INSTANCE,
            FloatAdapter.INSTANCE,
            BooleanAdapter.INSTANCE,
            StringAdapter.INSTANCE
    );

    private PrimitiveTypeAdapters() {
    }

    static class IntegerAdapter extends IntegerTypeAdapter implements BukkitTypeAdapter<Integer> {
        public static final IntegerAdapter INSTANCE = new IntegerAdapter();

        private IntegerAdapter() {
        }

        @Override
        public Integer get(ConfigurationSection configuration, String path) {
            return configuration.getInt(path);
        }

        @Override
        public void saveDefault(ConfigurationSection configuration, String path, Object defaultValue) {
            defaultValue = defaultValue != null ? defaultValue : 0;
            save(configuration, path, defaultValue);
        }
    }

    static class LongAdapter extends LongTypeAdapter implements BukkitTypeAdapter<Long> {
        public static final LongAdapter INSTANCE = new LongAdapter();

        private LongAdapter() {
        }

        @Override
        public Long get(ConfigurationSection configuration, String path) {
            return configuration.getLong(path);
        }

        @Override
        public void saveDefault(ConfigurationSection configuration, String path, Object defaultValue) {
            defaultValue = defaultValue != null ? defaultValue : 0L;
            save(configuration, path, defaultValue);
        }
    }

    static class DoubleAdapter extends DoubleTypeAdapter implements BukkitTypeAdapter<Double> {
        public static final DoubleAdapter INSTANCE = new DoubleAdapter();

        private DoubleAdapter() {
        }

        @Override
        public Double get(ConfigurationSection configuration, String path) {
            return configuration.getDouble(path);
        }

        @Override
        public void saveDefault(ConfigurationSection configuration, String path, Object defaultValue) {
            defaultValue = defaultValue != null ? defaultValue : 0D;
            save(configuration, path, defaultValue);
        }
    }

    static class FloatAdapter extends FloatTypeAdapter implements BukkitTypeAdapter<Float> {
        public static final FloatAdapter INSTANCE = new FloatAdapter();

        private FloatAdapter() {
        }

        @Override
        public Float get(ConfigurationSection configuration, String path) {
            return (float) configuration.getDouble(path);
        }

        @Override
        public void saveDefault(ConfigurationSection configuration, String path, Object defaultValue) {
            defaultValue = defaultValue != null ? defaultValue : 0F;
            save(configuration, path, defaultValue);
        }
    }

    static class BooleanAdapter extends BooleanTypeAdapter implements BukkitTypeAdapter<Boolean> {
        public static final BooleanAdapter INSTANCE = new BooleanAdapter();

        private BooleanAdapter() {
        }

        @Override
        public Boolean get(ConfigurationSection configuration, String path) {
            return configuration.getBoolean(path);
        }

        @Override
        public void saveDefault(ConfigurationSection configuration, String path, Object defaultValue) {
            defaultValue = defaultValue != null ? defaultValue : false;
            save(configuration, path, defaultValue);
        }
    }

    static class StringAdapter extends StringTypeAdapter implements BukkitTypeAdapter<String> {
        public static final StringAdapter INSTANCE = new StringAdapter();

        private StringAdapter() {
        }

        @Override
        public String get(ConfigurationSection configuration, String path) {
            return configuration.getString(path);
        }

        @Override
        public void saveDefault(ConfigurationSection configuration, String path, Object defaultValue) {
            defaultValue = defaultValue != null ? defaultValue : "";
            save(configuration, path, defaultValue);
        }
    }
}
