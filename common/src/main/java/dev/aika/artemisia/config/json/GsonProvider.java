package dev.aika.artemisia.config.json;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dev.aika.artemisia.config.json.serializer.ColorSerializer;
import dev.aika.artemisia.config.json.serializer.DurationSerializer;
import dev.aika.artemisia.config.json.serializer.FileSerializer;
import lombok.experimental.UtilityClass;

import java.awt.*;
import java.io.File;
import java.time.Duration;

@UtilityClass
public class GsonProvider {
    public final Gson GSON = gsonBuilder().setPrettyPrinting().create();

    public GsonBuilder gsonBuilder() {
        return new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .registerTypeAdapter(Color.class, new ColorSerializer())
                .registerTypeAdapter(Duration.class, new DurationSerializer())
                .registerTypeAdapter(File.class, new FileSerializer());
    }
}