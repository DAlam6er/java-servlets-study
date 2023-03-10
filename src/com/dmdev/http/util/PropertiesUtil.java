package com.dmdev.http.util;

import lombok.SneakyThrows;

import java.util.Properties;

public final class PropertiesUtil
{
    private static final Properties PROPERTIES = new Properties();

    static {
        loadProperties();
    }

    private PropertiesUtil() {throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");}

    @SneakyThrows
    private static void loadProperties()
    {
        try (var inputStream = PropertiesUtil.class.getClassLoader()
            .getResourceAsStream("application.properties"))
        {
            PROPERTIES.load(inputStream);
        }
    }

    public static String get(String key)
    {
        return PROPERTIES.getProperty(key);
    }
}
