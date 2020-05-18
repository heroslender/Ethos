package com.heroslender.ethos;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class ConfigurationLoaderTest {
    private MockConfigurationLoader<TestConfig> configurationLoader;

    @Before
    public void setup() {
        configurationLoader = new MockConfigurationLoader<>(new Object(), () -> {
        }, TestConfig.class);
    }

    @Test
    public void configurationLoaderShouldInitializeClass() {
        TestConfig config = configurationLoader.initClass();

        assertNotNull(config);
        assertNull(config.name);
    }

    private static class TestConfig {
        String name;
    }
}
