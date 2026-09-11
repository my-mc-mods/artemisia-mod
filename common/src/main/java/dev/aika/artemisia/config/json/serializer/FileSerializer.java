package dev.aika.artemisia.config.json.serializer;

import com.google.gson.*;

import java.io.File;
import java.lang.reflect.Type;
import java.util.Optional;

public class FileSerializer implements JsonSerializer<File>, JsonDeserializer<File> {
    @Override public JsonElement serialize(File src, Type typeOfSrc, JsonSerializationContext context) {
        return src == null ? JsonNull.INSTANCE : new JsonPrimitive(src.getAbsolutePath());
    }

    @Override public File deserialize(
            JsonElement json, Type typeOfT, JsonDeserializationContext context
    ) throws JsonParseException {
        return Optional.ofNullable(json)
                .filter(JsonElement::isJsonPrimitive)
                .map(JsonElement::getAsString)
                .map(File::new)
                .orElse(null);
    }
}