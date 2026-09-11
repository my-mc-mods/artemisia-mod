package dev.aika.artemisia.config;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Optional;
import java.util.StringJoiner;

@SuppressWarnings("unused")
@UtilityClass
public class ConfigHelper {
    public String buildKey(String modId, String configType, String categoryKey, CharSequence... elements) {
        StringJoiner joiner = new StringJoiner(".");
        joiner.add("config").add(modId);
        if (configType != null) joiner.add(configType);
        if (categoryKey != null) joiner.add(categoryKey);
        if (elements != null) for (CharSequence element : elements)
            if (element != null) joiner.add(element);
        return joiner.toString();
    }

    public <T> String buildKey(ConfigManager<T> manager, String categoryKey, CharSequence... elements) {
        return buildKey(manager.getModId(), manager.getType(), categoryKey, elements);
    }

    public <A extends Annotation> Optional<A> getAnno(Field field, Class<A> clazz) {
        return Optional.ofNullable(field.getDeclaredAnnotation(clazz));
    }

    public boolean isIgnored(Field field) {
        if (Modifier.isFinal(field.getModifiers())) return true;
        return field.getDeclaredAnnotation(Config.Ignored.class) != null;
    }

    @SneakyThrows
    public Object getValue(Field field, Object object) {
        return field.get(object);
    }
}