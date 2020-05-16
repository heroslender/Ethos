package com.heroslender.ethos.adapter.types;

import com.heroslender.ethos.adapter.TypeAdapter;

public class IntegerTypeAdapter implements TypeAdapter<Integer> {
    @Override
    public Class<Integer> getType() {
        return Integer.class;
    }

    @Override
    public Integer from(String value) {
        return Integer.parseInt(value);
    }
}
