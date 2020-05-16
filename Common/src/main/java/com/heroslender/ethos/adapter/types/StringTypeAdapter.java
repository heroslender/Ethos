package com.heroslender.ethos.adapter.types;

import com.heroslender.ethos.adapter.TypeAdapter;

public class StringTypeAdapter implements TypeAdapter<String> {
    @Override
    public Class<String> getType() {
        return String.class;
    }

    @Override
    public String from(String value) {
        return value;
    }
}
