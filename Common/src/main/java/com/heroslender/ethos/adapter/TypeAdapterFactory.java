package com.heroslender.ethos.adapter;

import com.heroslender.ethos.adapter.exceptions.AdapterNotFoundException;
import org.apache.commons.lang3.ClassUtils;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

public interface TypeAdapterFactory<C extends TypeAdapter<?>> {
    List<C> getAdapterList();

    @NotNull
    default <T> C getTypeAdapter(@NotNull final Class<T> type) throws AdapterNotFoundException {
        Objects.requireNonNull(type, "type is null");

        for (C adapter : getAdapterList()) {
            if (ClassUtils.isAssignable(type, adapter.getType(), true)) {
                return adapter;
            }
        }

        throw new AdapterNotFoundException(type);
    }
}
