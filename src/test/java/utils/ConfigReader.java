package utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {

    private static final Properties properties = new Properties();

    static {
        try (InputStream input = ConfigReader.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new RuntimeException("Unable to find config.properties in classpath (src/test/resources)");
            }
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config.properties", e);
        }
    }

    public static String getTargetBrowser() {
        String systemOverride = System.getProperty("TargetBrowser");
        if (systemOverride != null && !systemOverride.isBlank() && !systemOverride.startsWith("${")) {
            return systemOverride.trim().toLowerCase();
        }
        String value = properties.getProperty("TargetBrowser", "chrome");
        return value.trim().toLowerCase();
    }

    public static boolean isGridEnabled() {
        String override = System.getProperty("UseGrid");
        String value = (override != null && !override.isBlank() && !override.startsWith("${"))
                ? override
                : properties.getProperty("UseGrid", "false");
        return Boolean.parseBoolean(value.trim());
    }

    public static String getGridUrl() {
        String override = System.getProperty("GridUrl");
        if (override != null && !override.isBlank() && !override.startsWith("${")) {
            return override.trim();
        }
        return properties.getProperty("GridUrl", "http://localhost:4444").trim();
    }
}
