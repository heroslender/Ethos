package com.heroslender.ethos.bukkit.adapter;

import com.google.common.collect.Lists;
import com.heroslender.ethos.bukkit.adapter.types.ListTypeAdapter;
import com.heroslender.ethos.bukkit.adapter.types.MapTypeAdapter;
import com.heroslender.ethos.bukkit.adapter.types.ObjectTypeAdapter;
import com.heroslender.ethos.bukkit.adapter.types.PrimitiveTypeAdapters;
import com.heroslender.ethos.adapter.TypeAdapterFactory;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class BukkitTypeAdapterFactory implements TypeAdapterFactory<BukkitTypeAdapter<?>> {
    private static BukkitTypeAdapterFactory instance;

    private static List<BukkitTypeAdapter<?>> adapterList;

    private BukkitTypeAdapterFactory() {
    }

    @NotNull
    @Override
    public synchronized List<BukkitTypeAdapter<?>> getAdapterList() {
        if (adapterList == null) {
            adapterList = Lists.newArrayList();
            adapterList.addAll(PrimitiveTypeAdapters.PRIMITIVE_ADAPTERS);
            adapterList.add(ListTypeAdapter.INSTANCE);
            adapterList.add(MapTypeAdapter.INSTANCE);

            adapterList.add(ObjectTypeAdapter.INSTANCE);
        }

        return adapterList;
    }

    @NotNull
    public static synchronized BukkitTypeAdapterFactory getInstance() {
        if (instance == null) {
            instance = new BukkitTypeAdapterFactory();
        }

        return instance;
    }
}
