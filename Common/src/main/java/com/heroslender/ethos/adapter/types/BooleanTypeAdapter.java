package com.heroslender.ethos.adapter.types;

import com.heroslender.ethos.adapter.TypeAdapter;

public class BooleanTypeAdapter implements TypeAdapter<Boolean> {
    @Override
    public Class<Boolean> getType() {
        return Boolean.TYPE;
    }

    @Override
    public Boolean from(String value) {
        return Boolean.parseBoolean(value);
    }
}
