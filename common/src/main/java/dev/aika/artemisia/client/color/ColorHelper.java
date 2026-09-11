package dev.aika.artemisia.client.color;

import lombok.experimental.UtilityClass;

import java.awt.*;

@SuppressWarnings("unused")
@UtilityClass
public class ColorHelper {
    public String toRgbaHex(Color color) {
        if (color == null) return null;
        final int alpha = color.getAlpha();
        final int rgb = color.getRGB();
        if (alpha == 0xFF) return String.format("#%06X", rgb & 0xFFFFFF);
        return String.format("#%08X", ((rgb & 0xFFFFFF) << 8) | alpha);
    }

    public String toRgbaHex(CommonColor color) {
        return toRgbaHex(color.color());
    }

    public String toArgbHex(Color color) {
        if (color == null) return null;
        if (color.getAlpha() == 0xFF) return String.format("#%06X", color.getRGB());
        return String.format("#%08X", color.getRGB());
    }

    public String toArgbHex(CommonColor color) {
        return toArgbHex(color.color());
    }

    public Color toColor(String hex, boolean... isArgb) {
        if (hex == null || hex.isEmpty())
            throw new IllegalArgumentException("Hex string cannot be null or empty");

        if (hex.startsWith("#")) hex = hex.substring(1);
        if (hex.startsWith("0x")) hex = hex.substring(2);

        if (hex.length() == 6) return new Color(Integer.parseInt(hex, 16));
        else if (hex.length() == 8) {
            final int rgb = Integer.parseInt(
                    (isArgb.length > 0 && isArgb[0]) ? hex.substring(2, 8) : hex.substring(0, 6),
                    16);
            return new Color(
                    (rgb >> 16) & 0xFF,
                    (rgb >> 8) & 0xFF,
                    rgb & 0xFF,
                    Integer.parseInt(
                            (isArgb.length > 0 && isArgb[0]) ? hex.substring(0, 2) : hex.substring(6, 8),
                            16)
            );
        }
        throw new IllegalArgumentException("Invalid hex color format. Must be 6 or 8 characters long.");
    }

    public Color toColor(String hex, Color defaultValue, boolean... isArgb) {
        try {
            return toColor(hex, isArgb);
        } catch (Exception e) {
            return defaultValue;
        }
    }
}