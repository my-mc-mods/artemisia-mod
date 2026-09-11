package dev.aika.artemisia.script;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lombok.SneakyThrows;

import java.time.Duration;

public record ScriptManager(
        Cache<String, EasyScript> cache,
        boolean stripDebug
) {
    public ScriptManager(long maxSize, Duration duration, boolean stripDebug) {
        this(CacheBuilder.newBuilder()
                        .maximumSize(maxSize).expireAfterAccess(duration)
                        .build(),
                stripDebug);
    }

    public ScriptManager(boolean stripDebug) {
        this(10, Duration.ofMinutes(10), stripDebug);
    }

    @SneakyThrows
    public EasyScript get(String code) {
        return cache.get(code, () -> new EasyScript(code, stripDebug));
    }

    @SneakyThrows
    public EasyScript getExpression(String code) {
        return cache.get(code, () -> EasyScript.expression(code));
    }
}