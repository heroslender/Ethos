package com.heroslender.ethos.adapter.types;

import com.heroslender.ethos.adapter.TypeAdapter;

public class FloatTypeAdapter implements TypeAdapter<Float> {
    @Override
    public Class<Float> getType() {
        return Float.TYPE;
    }

    @Override
    public Float from(String value) {
        return Float.parseFloat(value);
    }
}
