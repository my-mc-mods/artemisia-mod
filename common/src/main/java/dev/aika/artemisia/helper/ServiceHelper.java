package dev.aika.artemisia.helper;

import dev.aika.artemisia.Constants;

import java.util.ServiceLoader;

public class ServiceHelper {
    public static <T> T load(Class<T> clazz) {
        final T loadedService = ServiceLoader.load(clazz, ServiceHelper.class.getClassLoader())
                .findFirst()
                .orElseThrow(() -> new NullPointerException("Failed to load service for " + clazz.getName()));
        Constants.LOG.debug("Loaded {} for service {}", loadedService, clazz);
        return loadedService;
    }
}