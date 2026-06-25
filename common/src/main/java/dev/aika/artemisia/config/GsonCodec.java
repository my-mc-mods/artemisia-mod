package dev.aika.artemisia.config;

import com.google.gson.Gson;

import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;

public class GsonCodec<T> implements ConfigCodec<T> {
    private final Gson gson;
    private final Type type;

    public GsonCodec(Gson gson, Type type) {
        this.gson = gson;
        this.type = type;
    }

    @Override public void encode(Writer writer, T value) {
        gson.toJson(value, type, writer);
    }

    @Override public T decode(Reader reader) {
        return gson.fromJson(reader, type);
    }
}