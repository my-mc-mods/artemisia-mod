package dev.aika.artemisia.config;

import com.google.gson.Gson;
import dev.aika.artemisia.config.annotations.Config;
import dev.aika.artemisia.json.GsonProvider;
import dev.aika.artemisia.json.serializer.IdentifierSerializer;
import lombok.Getter;
import lombok.SneakyThrows;
import net.minecraft.resources.Identifier;

import java.io.*;
import java.nio.file.Path;
import java.util.function.Supplier;

@SuppressWarnings({"unused", "UnusedReturnValue"})
public class ConfigManager<T> implements Supplier<T> {
    private final Supplier<T> defaultValueFactory;
    private final ConfigCodec<T> codec;
    //    private final Config annotation;
    private T value;
    @Getter
    private final File file;

    public ConfigManager(Supplier<T> defaultValueFactory, ConfigCodec<T> codec, Path configDir) {
        value = defaultValueFactory.get();
        Config annotation = value.getClass().getAnnotation(Config.class);
        if (annotation == null) throw new IllegalStateException("@Config is not present");
        file = new File(configDir.toFile(),
                String.format("%s-%s.json", annotation.value(), annotation.type()));
        this.defaultValueFactory = defaultValueFactory;
        this.codec = codec;
    }

    public ConfigManager(Supplier<T> defaultValueFactory, Gson gson, Path configDir) {
        this(defaultValueFactory, new GsonCodec<>(gson, defaultValueFactory.get().getClass()), configDir);
    }

    public ConfigManager(Supplier<T> defaultValueFactory, Path configDir) {
        this(defaultValueFactory, GsonProvider.GSON, configDir);
    }

    public static <T> ConfigManager<T> create(
            Supplier<T> defaultValueFactory, Path configDir
    ) {
        return new ConfigManager<>(
                defaultValueFactory,
                GsonProvider.gsonBuilder()
                        .registerTypeAdapter(Identifier.class, new IdentifierSerializer())
                        .setPrettyPrinting()
                        .create(),
                configDir
        );
    }

    @Override public T get() {
        return value;
    }

    @SneakyThrows
    public ConfigManager<T> save() {
        try (var writer = new FileWriter(file)) {
            write(writer, value);
        }
        return this;
    }

    @SneakyThrows
    public ConfigManager<T> load() {
        if (!file.exists()) save();
        else try (var reader = new FileReader(file)) {
            value = read(reader);
        }
        return this;
    }

    private void write(Writer writer, T value) {
        codec.encode(writer, value);
    }

    private T read(Reader reader) {
        value = codec.decode(reader);
        return value;
    }

    public T getDefaultValue() {
        return defaultValueFactory.get();
    }

    public void reset() {
        value = defaultValueFactory.get();
    }
}