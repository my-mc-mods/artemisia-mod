package dev.aika.artemisia.config;

import dev.aika.artemisia.platform.PlatformHelper;
import dev.aika.artemisia.config.json.GsonProvider;
import dev.aika.artemisia.config.json.serializer.IdentifierSerializer;
import lombok.Getter;
import lombok.SneakyThrows;
import net.minecraft.resources.Identifier;

import java.io.*;
import java.nio.file.Path;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public class ConfigManager<T> implements Supplier<T> {
    @Getter
    private final Class<T> configClass;
    @Getter
    private final Config configAnnotation;
    private final Supplier<T> defaultSupplier;
    private final ConfigCodec<T> codec;
    @Getter
    private final Path configDirectory;
    @Getter
    private final File file;

    private T config;

    private ConfigManager(Class<T> clazz, Supplier<T> defaultSupplier, ConfigCodec<T> codec, Path configDir) {
        this.configAnnotation = clazz.getAnnotation(Config.class);
        if (configAnnotation == null) throw new IllegalStateException("@Config is not present");
        this.configClass = clazz;
        this.configDirectory = configDir;
        this.defaultSupplier = defaultSupplier;
        this.codec = codec;
        this.file = new File(configDir.toFile(),
                String.format("%s-%s.json", configAnnotation.value(), configAnnotation.type()));
        this.config = defaultSupplier.get();
    }

    public static <T> ConfigManager<T> create(Class<T> clazz, Supplier<T> defaultSupplier, ConfigCodec<T> codec, Path configDir) {
        return new ConfigManager<>(clazz, defaultSupplier, codec, configDir);
    }

    public static <T> ConfigManager<T> create(Class<T> clazz, Supplier<T> defaultSupplier, Path configDir) {
        return create(clazz, defaultSupplier,
                new GsonCodec<>(GsonProvider.gsonBuilder()
                        .registerTypeAdapter(Identifier.class, new IdentifierSerializer())
                        .disableHtmlEscaping().setPrettyPrinting()
                        .create(), clazz),
                configDir);
    }

    public static <T> ConfigManager<T> create(Class<T> clazz, Supplier<T> defaultSupplier) {
        return create(clazz, defaultSupplier, PlatformHelper.get().getConfigDir());
    }

    public static <T> ConfigManager<T> create(Class<T> clazz) {
        return create(clazz, () -> createDefaultConfig(clazz));
    }

    private static <T> T createDefaultConfig(Class<T> clazz) {
        try {
            return clazz.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Could not instantiate " + clazz.getName(), e);
        }
    }

    @Override
    public T get() {
        return config;
    }

    @SneakyThrows
    public ConfigManager<T> save() {
        try (var writer = new FileWriter(file)) {
            write(writer, config);
        }
        return this;
    }

    @SneakyThrows
    public ConfigManager<T> load() {
        if (!file.exists()) return save();
        else try (var reader = new FileReader(file)) {
            config = read(reader);
        }
        return this;
    }

    private void write(Writer writer, T value) {
        codec.encode(writer, value);
    }

    private T read(Reader reader) {
        config = codec.decode(reader);
        return config;
    }

    public T getDefaultConfig() {
        return defaultSupplier.get();
    }

    public String getModId() {
        return configAnnotation.value();
    }

    public String getDefaultCategory() {
        return configAnnotation.defaultCategory();
    }

    public String getType() {
        return configAnnotation.type();
    }
}