package com.heroslender.ethos.bukkit;

import com.heroslender.ethos.annotations.Optional;
import com.heroslender.ethos.annotations.SerializedName;
import org.bukkit.configuration.file.YamlConfiguration;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BukkitConfigurationLoaderTest {
    private YamlConfiguration yamlConfiguration;
    private BukkitConfigurationLoader<TestConfig> configurationLoader;

    @Before
    public void setup() {
        yamlConfiguration = new YamlConfiguration();
        configurationLoader = new BukkitConfigurationLoader<>(yamlConfiguration, () -> {
        }, TestConfig.class);
    }

    @Test
    public void shouldSaveDefaults() {
        configurationLoader.load();

        String name = yamlConfiguration.getString("firstName");
        assertNotNull(name);
        assertEquals("Your name", name);

        // Optional field
        String lastName = yamlConfiguration.getString("lastName");
        assertNull(lastName);

        int age = yamlConfiguration.getInt("age");
        assertEquals(0, age);
    }

    @Test
    public void shouldLoadProperly() {
        // Fake some changed config value
        yamlConfiguration.set("lastName", "Anonymous");

        TestConfig config = configurationLoader.load();

        assertNotNull(config.name);
        assertEquals("Your name", config.name);

        assertNotNull(config.lastName);
        assertEquals("Anonymous", config.lastName);

        assertEquals(0, config.age);
    }

    @Test
    public void shouldSaveProperly() {
        TestConfig config = new TestConfig();

        config.name = "Foo";
        config.lastName = "Bar";
        config.age = 18;

        configurationLoader.save(config);

        assertEquals("Foo", yamlConfiguration.getString("firstName"));
        assertEquals("Bar", yamlConfiguration.getString("lastName"));
        assertEquals(18, yamlConfiguration.getInt("age"));
    }

    private static class TestConfig {
        @SerializedName("firstName")
        String name = "Your name";

        @Optional
        String lastName = "Your surname";

        int age = 0;
    }
}
