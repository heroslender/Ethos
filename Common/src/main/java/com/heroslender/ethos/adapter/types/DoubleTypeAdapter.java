package com.heroslender.ethos.adapter.types;

import com.heroslender.ethos.adapter.TypeAdapter;

public class DoubleTypeAdapter implements TypeAdapter<Double> {
    @Override
    public Class<Double> getType() {
        return Double.TYPE;
    }

    @Override
    public Double from(String value) {
        return Double.parseDouble(value);
    }
}
