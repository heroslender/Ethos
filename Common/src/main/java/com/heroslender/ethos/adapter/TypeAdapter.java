package com.heroslender.ethos.adapter;

public interface TypeAdapter<T> {
    Class<T> getType();

    T from(String value);
}