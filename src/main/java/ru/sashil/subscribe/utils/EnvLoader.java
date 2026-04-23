package ru.sashil.subscribe.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class EnvLoader {
    private static final Map<String, String> env = new HashMap<>();

    static {
        try {
            Files.readAllLines(Paths.get(".env")).forEach(line -> {
                if (line.contains("=") && !line.startsWith("#")) {
                    String[] parts = line.split("=", 2);
                    env.put(parts[0].trim(), parts[1].trim());
                }
            });
        } catch (IOException e) {
            System.err.println("Не удалось загрузить .env файл: " + e.getMessage());
        }
    }

    public static String get(String key) {
        return env.getOrDefault(key, System.getenv(key));
    }
}
