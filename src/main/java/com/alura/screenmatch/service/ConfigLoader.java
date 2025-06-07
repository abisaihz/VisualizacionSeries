package com.alura.screenmatch.service;

import java.io.InputStream;
import java.util.Properties;

public class ConfigLoader {
    private static Properties properties = new Properties();

    static {
        try (InputStream input = ConfigLoader.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                System.out.println("No se encontró el archivo config.properties");

            }
            properties.load(input);
            System.out.println("Asignacion completa" + properties.getProperty("api_key"));
        } catch (Exception e) {
            System.out.println("Error al cargar el archivo de configuración: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public static String getProperty(String key) {
        System.out.println("la clave es: " + properties.getProperty(key));
        return properties.getProperty(key);
    }
}
