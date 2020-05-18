<img src="https://github.com/heroslender.png?size=96" alt="Elytra" title="Elytra" align="right"/>

## [WIP] Ethos

[![GitHub stars](https://img.shields.io/github/stars/heroslender/Ethos.svg)](https://github.com/heroslender/Ethos/stargazers)
[![GitHub issues](https://img.shields.io/github/issues-raw/heroslender/Ethos.svg?label=issues)](https://github.com/heroslender/Ethos/issues)
[![GitHub last commit](https://img.shields.io/github/last-commit/heroslender/Ethos.svg)](https://github.com/heroslender/Ethos/commit)
[![MIT License](https://img.shields.io/badge/license-MIT-blue.svg?color=1bcc1b)](https://choosealicense.com/licenses/mit)
[![Open Source Love](https://badges.frapsoft.com/os/v1/open-source.png?v=103)](https://github.com/ellerbrock/open-source-badges/)

Ethos is an ORM targeted for configuration files. It features annotations for data validation and more!

This project came with the need to remove boilerplate code for Bukkit plugins when dealing with configuration files, dealing
with data serialization/deserialization, data validation, etc.

### Example
```java
public class Main extends JavaPlugin {
    private BukkitConfigurationLoader configurationLoader;
    public TestConfig config;

    @Override
    public void onEnable() {
        // Create a new configuration loader for bukkit's yaml config system
        configurationLoader = new BukkitConfigurationLoader<>(
            // The configuration section
            getConfig(),
            // A runnable to save the configuration
            this::saveConfig,
            // The configuration class
            TestConfig.class
        );

        // Load a new config instance
        config = configurationLoader.load();
        
        getLogger().info("Hello " + config.name + " " + config.lastName);
    }
}

public class TestConfig {
    @SerializedName("firstName")
    String name = "Your name";

    @Optional
    String lastName = "Your surname";

    int age = 0;
}
```

### Usage

#### Maven

```xml
<repositories>
    <repository>
        <id>heroslender-repo</id>
        <url>https://nexus.heroslender.com/repository/maven-public/</url>
    </repository>
</repositories>

<dependencies>
    <dependency>
        <groupId>com.heroslender.ethos</groupId>
        <artifactId>ethos-bukkit</artifactId>
        <version>1.0.0-SNAPSHOT</version>
        <scope>compile</scope>
    </dependency>
</dependencies>
```


#### Gradle

```groovy
repositories {
    maven {
        url "https://nexus.heroslender.com/repository/maven-public/"
    }
}

dependencies {
    compile "com.heroslender.ethos:ethos-bukkit:1.0.0-SNAPSHOT"
}
```


#### Gradle Kts

```kotlin
repositories {
    maven("https://nexus.heroslender.com/repository/maven-public/")
}

dependencies {
    implementation("com.heroslender.ethos:ethos-bukkit:1.0.0-SNAPSHOT")
}
```