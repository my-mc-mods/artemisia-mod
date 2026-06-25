package dev.aika.artemisia.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dev.aika.artemisia.json.serializer.ColorSerializer;
import dev.aika.artemisia.json.serializer.DurationSerializer;
import lombok.experimental.UtilityClass;

import java.awt.*;
import java.time.Duration;

@UtilityClass
public class GsonProvider {
    public final Gson GSON = gsonBuilder().setPrettyPrinting().create();

    public GsonBuilder gsonBuilder() {
        return new GsonBuilder()
                .registerTypeAdapter(Color.class, new ColorSerializer())
                .registerTypeAdapter(Duration.class, new DurationSerializer());
    }
}