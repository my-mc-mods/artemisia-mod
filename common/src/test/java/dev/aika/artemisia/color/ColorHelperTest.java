package dev.aika.artemisia.color;

import dev.aika.artemisia.client.color.ColorHelper;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ColorHelperTest {
    @Test
    public void testToColor() {
        final var color = new Color(0xFF, 0x00, 0x00, 0xFF);
        assertEquals(color, ColorHelper.toColor("#FFFF0000", true));
        assertEquals(color, ColorHelper.toColor("#FF0000", true));
        assertEquals(color, ColorHelper.toColor("#FF0000"));
        assertEquals(color, ColorHelper.toColor("#FF0000FF"));
    }

    @Test
    public void testToRgbaHex() {
        final var color = new Color(0xFF, 0x00, 0x00, 0xFF);
        assertEquals("#FF0000", ColorHelper.toRgbaHex(color));
    }

    @Test
    public void testToArgbHex() {
        final var color = new Color(0xFF, 0x00, 0x00, 0xFF);
        assertEquals("#FFFF0000", ColorHelper.toArgbHex(color));
    }
}