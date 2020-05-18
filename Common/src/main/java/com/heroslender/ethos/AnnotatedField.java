package com.heroslender.ethos;

import com.heroslender.ethos.annotations.NotSerialized;
import com.heroslender.ethos.annotations.Optional;
import com.heroslender.ethos.annotations.SerializedName;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Objects;

public class AnnotatedField {
    private final Class<?> type;
    private final Type genericType;
    private final String serializedName;
    private final boolean optional;
    private final boolean notSerialized;

    public AnnotatedField(Type genericType, String serializedName) {
        this((Class<?>) genericType, genericType, serializedName, false, false);
    }

    public AnnotatedField(Field field, String serializedName, boolean optional, boolean notSerialized) {
        this(field.getType(), field.getGenericType(), serializedName, optional, notSerialized);
    }

    public AnnotatedField(Class<?> type, Type genericType, String serializedName, boolean optional, boolean notSerialized) {
        this.type = type;
        this.genericType = genericType;
        this.serializedName = serializedName;
        this.optional = optional;
        this.notSerialized = notSerialized;
    }

    public Class<?> getType() {
        return type;
    }

    public Type getGenericType() {
        return genericType;
    }

    public String getSerializedName() {
        return serializedName;
    }

    public boolean isOptional() {
        return optional;
    }

    public boolean isNotSerialized() {
        return notSerialized;
    }

    public static AnnotatedField load(@NotNull final Field field) {
        Objects.requireNonNull(field, "field is null");

        field.setAccessible(true);

        Builder builder = new Builder(field);
        SerializedName name = field.getAnnotation(SerializedName.class);
        if (name != null) {
            builder.setSerializedName(name.value());
        } else {
            builder.setSerializedName(field.getName());
        }

        builder.setOptional(field.isAnnotationPresent(Optional.class));
        builder.setNotSerialized(field.isAnnotationPresent(NotSerialized.class));

        return builder.build();
    }

    public static class Builder {
        private final Field field;
        private String serializedName;
        private boolean optional;
        private boolean notSerialized;

        public Builder(Field field) {
            this.field = field;
            this.serializedName = field.getName();
            this.optional = false;
            this.notSerialized = false;
        }

        public String getSerializedName() {
            return serializedName;
        }

        public void setSerializedName(String serializedName) {
            this.serializedName = serializedName;
        }

        public boolean isOptional() {
            return optional;
        }

        public void setOptional(boolean optional) {
            this.optional = optional;
        }

        public boolean isNotSerialized() {
            return notSerialized;
        }

        public void setNotSerialized(boolean notSerialized) {
            this.notSerialized = notSerialized;
        }

        public AnnotatedField build() {
            return new AnnotatedField(field, serializedName, optional, notSerialized);
        }
    }
}
