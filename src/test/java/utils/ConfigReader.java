package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    private static Properties properties = new Properties();

    static {
        loadFile("src/test/resources/config.properties");

        // Load local secrets file if present (never committed to repo)
        File localFile = new File("src/test/resources/local.properties");
        if (localFile.exists()) {
            loadFile("src/test/resources/local.properties");
        }

        String env = System.getProperty("env", "qa");
        String envPath = "src/test/resources/" + env + ".properties";

        File envFile = new File(envPath);
        if (envFile.exists()) {
            loadFile(envPath);
        }
    }

    private static void loadFile(String path) {
        try (FileInputStream fis = new FileInputStream(path)) {
            properties.load(fis);
        } catch (IOException e) {
            throw new RuntimeException("Could not load config file: " + path, e);
        }
    }

    public static String get(String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            value = System.getenv(key);
        }
        if (value == null) {
            throw new RuntimeException("Property '" + key + "' not found in config");
        }
        return value;
    }

    public static Long getLong(String key) {
        String value = properties.getProperty(key);
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException e) {
            throw new RuntimeException("Config key '" + key + "' expected a long but got: '" + value + "'");
        }
    }
}