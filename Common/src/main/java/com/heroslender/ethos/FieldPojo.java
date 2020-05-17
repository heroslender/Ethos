package com.heroslender.ethos;

import java.lang.reflect.Field;

public class FieldPojo {
    private final Field field;
    private final String serializedName;
    private final boolean optional;
    private final boolean notSerialized;

    public FieldPojo(Field field, String serializedName, boolean optional, boolean notSerialized) {
        this.field = field;
        this.serializedName = serializedName;
        this.optional = optional;
        this.notSerialized = notSerialized;
    }

    public Field getField() {
        return field;
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

    public static class Builder {
        private final Field field;
        private String serializedName;
        private boolean optional;
        private boolean notSerialized;

        public Builder(Field field) {
            this.field = field;
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

        public FieldPojo build() {
            return new FieldPojo(field, serializedName, optional, notSerialized);
        }
    }
}
