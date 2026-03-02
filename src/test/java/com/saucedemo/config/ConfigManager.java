package com.saucedemo.config;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Centralized configuration manager.
 * Loads properties from config.properties and allows overrides via system properties.
 */
@Slf4j
public class ConfigManager {

    private static final Properties properties = new Properties();
    private static final String CONFIG_FILE = "config.properties";

    static {
        loadProperties();
    }

    private ConfigManager() {}

    private static void loadProperties() {
        try (InputStream inputStream = ConfigManager.class.getClassLoader().getResourceAsStream(CONFIG_FILE)) {
            if (inputStream == null) {
                throw new RuntimeException("Configuration file not found: " + CONFIG_FILE);
            }
            properties.load(inputStream);
            log.info("Configuration loaded from {}", CONFIG_FILE);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load configuration: " + e.getMessage(), e);
        }
    }

    public static String get(String key) {
        String systemValue = System.getProperty(key);
        if (systemValue != null && !systemValue.isBlank()) {
            return systemValue;
        }
        return properties.getProperty(key);
    }

    public static String get(String key, String defaultValue) {
        String value = get(key);
        return (value != null && !value.isBlank()) ? value : defaultValue;
    }

    public static boolean getBoolean(String key, boolean defaultValue) {
        String value = get(key);
        return (value != null) ? Boolean.parseBoolean(value) : defaultValue;
    }

    public static int getInt(String key, int defaultValue) {
        String value = get(key);
        if (value == null) return defaultValue;
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            log.warn("Invalid integer value for key '{}': {}. Using default: {}", key, value, defaultValue);
            return defaultValue;
        }
    }

    public static String getBaseUrl() {
        return get("base.url", "https://www.saucedemo.com");
    }

    public static String getBrowser() {
        return get("browser", "chrome");
    }

    public static boolean isHeadless() {
        return getBoolean("headless", false);
    }

    public static int getImplicitWait() {
        return getInt("implicit.wait", 10);
    }

    public static int getExplicitWait() {
        return getInt("explicit.wait", 15);
    }

    public static int getPageLoadTimeout() {
        return getInt("page.load.timeout", 30);
    }

    public static String getValidUsername() {
        return get("valid.username", "standard_user");
    }

    public static String getValidPassword() {
        return get("valid.password", "secret_sauce");
    }

    public static String getLockedUsername() {
        return get("locked.username", "locked_out_user");
    }
}
