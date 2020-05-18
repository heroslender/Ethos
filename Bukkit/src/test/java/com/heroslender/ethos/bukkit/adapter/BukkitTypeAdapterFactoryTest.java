package com.heroslender.ethos.bukkit.adapter;

import com.heroslender.ethos.bukkit.BukkitConfigurationLoader;
import com.heroslender.ethos.bukkit.adapter.types.ListTypeAdapter;
import com.heroslender.ethos.bukkit.adapter.types.MapTypeAdapter;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class BukkitTypeAdapterFactoryTest {
    private BukkitTypeAdapterFactory typeAdapterFactory;

    @Before
    public void setup() {
        this.typeAdapterFactory = BukkitConfigurationLoader.TYPE_ADAPTER_FACTORY;
    }

    @Test
    public void shouldReturnValidAdapter() {
        BukkitTypeAdapter adapter = typeAdapterFactory.getTypeAdapter(int.class);
        assertNotNull(adapter);

        adapter = typeAdapterFactory.getTypeAdapter(List.class);
        assertNotNull(adapter);
        assertEquals(ListTypeAdapter.class, adapter.getClass());

        adapter = typeAdapterFactory.getTypeAdapter(HashMap.class);
        assertNotNull(adapter);
        assertEquals(MapTypeAdapter.class, adapter.getClass());
    }
}
