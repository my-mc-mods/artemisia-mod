package dev.aika.artemisia.json.serializer;

import com.google.gson.*;
import dev.aika.artemisia.json.CommonColor;

import java.awt.Color;
import java.lang.reflect.Type;
import java.util.Optional;

public class ColorSerializer implements JsonSerializer<Color>, JsonDeserializer<Color> {
    @Override
    public JsonElement serialize(Color src, Type typeOfSrc, JsonSerializationContext context) {
        return src == null ? JsonNull.INSTANCE : new JsonPrimitive(String.format("#%06X", src.getRGB() & 0xFFFFFF));
    }

    @Override
    public Color deserialize(
            JsonElement json, Type typeOfT, JsonDeserializationContext context
    ) throws JsonParseException {
        return Optional.ofNullable(json)
                .filter(JsonElement::isJsonPrimitive)
                .map(JsonElement::getAsString)
                .map(str -> {
                    final var c = CommonColor.fromName(str);
                    if (c != null) return c.getColor();
                    return Color.decode(str);
                })
                .orElse(null);
    }
}