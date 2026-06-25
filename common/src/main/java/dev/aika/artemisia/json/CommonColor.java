package dev.aika.artemisia.json;

import lombok.Getter;

import java.awt.*;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum CommonColor {
    WHITE(Color.WHITE),
    LIGHT_GRAY(Color.LIGHT_GRAY),
    GRAY(Color.GRAY),
    DARK_GRAY(Color.DARK_GRAY),
    BLACK(Color.BLACK),
    RED(Color.RED),
    PINK(Color.PINK),
    ORANGE(Color.ORANGE),
    YELLOW(Color.YELLOW),
    GREEN(Color.GREEN),
    MAGENTA(Color.MAGENTA),
    CYAN(Color.CYAN),
    BLUE(Color.BLUE);

    @Getter
    private final Color color;
    private static final Map<String, CommonColor> LOOKUP = Arrays.stream(values())
            .collect(Collectors.toMap(
                    e -> normalize(e.name()),
                    Function.identity()
            ));

    CommonColor(Color color) {
        this.color = color;
    }

    public static CommonColor fromName(String name) {
        if (name == null) return null;
        return LOOKUP.get(normalize(name));
    }

    private static String normalize(String str) {
        return str.toLowerCase()
                .replace("-", "")
                .replace("_", "");
    }
}