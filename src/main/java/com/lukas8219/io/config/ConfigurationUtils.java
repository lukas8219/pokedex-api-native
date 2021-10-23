package com.lukas8219.io.config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConfigurationUtils {

    private final static String CONFIGURATION_FILE = "application.properties";

    public static String getConfiguration(String key) {
        var env = System.getenv().get(key);
        if (env == null) {
            return ConfigurationUtils.parseKey(key);
        } else {
            return env;
        }
    }

    public static String parseKey(String key) {
        var resource = ConfigurationUtils.class.getClassLoader().getResource(CONFIGURATION_FILE);
        try (var reader = new BufferedReader(new InputStreamReader(resource.openStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains(key) && line.contains("=")) {
                    var parsed = line.split("=");
                    return parsed[1];
                }
            }
            throw new RuntimeException(String.format("Key %s not found", key));
        } catch (IOException e) {
            throw new RuntimeException(String.format("Unable to parse key %s", key));
        }
    }

}
