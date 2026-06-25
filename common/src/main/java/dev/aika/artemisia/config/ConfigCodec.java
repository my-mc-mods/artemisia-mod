package dev.aika.artemisia.config;

import java.io.Reader;
import java.io.Writer;

public interface ConfigCodec<T> {
    void encode(Writer writer, T value);

    T decode(Reader reader);
}