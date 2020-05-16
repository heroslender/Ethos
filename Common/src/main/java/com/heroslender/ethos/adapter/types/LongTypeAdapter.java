package com.heroslender.ethos.adapter.types;

import com.heroslender.ethos.adapter.TypeAdapter;

public class LongTypeAdapter implements TypeAdapter<Long> {
    @Override
    public Class<Long> getType() {
        return Long.TYPE;
    }

    @Override
    public Long from(String value) {
        return Long.parseLong(value);
    }
}
