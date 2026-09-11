package dev.aika.artemisia.config.json.serializer;

import com.google.gson.*;
import dev.aika.artemisia.client.color.CommonColor;
import dev.aika.artemisia.client.color.ColorHelper;

import java.awt.*;
import java.lang.reflect.Type;
import java.util.Optional;

public class ColorSerializer implements JsonSerializer<Color>, JsonDeserializer<Color> {
    @Override
    public JsonElement serialize(Color src, Type typeOfSrc, JsonSerializationContext context) {
        return src == null ? JsonNull.INSTANCE : new JsonPrimitive(ColorHelper.toArgbHex(src));
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
                    return c != null ? c.color() : ColorHelper.toColor(str);
                })
                .orElse(null);
    }
}