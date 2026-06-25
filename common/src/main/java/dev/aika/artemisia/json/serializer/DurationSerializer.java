package dev.aika.artemisia.json.serializer;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.Duration;
import java.util.Optional;

public class DurationSerializer implements JsonSerializer<Duration>, JsonDeserializer<Duration> {
    @Override
    public JsonElement serialize(Duration src, Type typeOfSrc, JsonSerializationContext context) {
        return src == null ? JsonNull.INSTANCE : new JsonPrimitive(src.toString());
    }

    @Override
    public Duration deserialize(
            JsonElement json, Type typeOfT, JsonDeserializationContext context
    ) throws JsonParseException {
        return Optional.ofNullable(json)
                .filter(JsonElement::isJsonPrimitive)
                .map(JsonElement::getAsString)
                .map(Duration::parse)
                .orElse(null);
    }
}