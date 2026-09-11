package dev.aika.artemisia.config.json.serializer;

import com.google.gson.*;
import net.minecraft.resources.Identifier;

import java.lang.reflect.Type;
import java.util.Optional;

public class IdentifierSerializer implements JsonSerializer<Identifier>, JsonDeserializer<Identifier> {
    @Override
    public JsonElement serialize(Identifier src, Type typeOfSrc, JsonSerializationContext context) {
        return src == null ? JsonNull.INSTANCE : new JsonPrimitive(src.toString());
    }

    @Override
    public Identifier deserialize(
            JsonElement json, Type typeOfT, JsonDeserializationContext context
    ) throws JsonParseException {
        return Optional.ofNullable(json)
                .filter(JsonElement::isJsonPrimitive)
                .map(JsonElement::getAsString)
                .map(Identifier::tryParse)
                .orElse(null);
    }
}