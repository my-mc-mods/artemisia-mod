package dev.aika.artemisia.json;

import lombok.Getter;
import org.junit.jupiter.api.Test;

import java.awt.Color;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

class GsonProviderTest {
    static class TestColorObject {
        @Getter
        Color color = Color.RED;
    }

    @Test
    void testColor() {
        final var jsonStr = "{\"color\":\"#FF0000\"}";
        assertEquals("{\n  \"color\": \"#FF0000\"\n}", GsonProvider.GSON.toJson(new TestColorObject()));
        assertEquals(jsonStr, GsonProvider.gsonBuilder().create().toJson(new TestColorObject()));

        final var obj1 = GsonProvider.GSON.fromJson(jsonStr, TestColorObject.class);
        assertNotNull(obj1);
        assertEquals(Color.RED, obj1.color);
        final var obj2 = GsonProvider.gsonBuilder().create().fromJson(jsonStr, TestColorObject.class);
        assertNotNull(obj2);
        assertEquals(Color.RED, obj2.color);
    }

    static class TestDurationObject {
        @Getter
        Duration duration = Duration.ofMinutes(30);
    }

    @Test
    void testDuration() {
        final var jsonStr = "{\"duration\":\"PT30M\"}";

        assertEquals("{\n  \"duration\": \"PT30M\"\n}", GsonProvider.GSON.toJson(new TestDurationObject()));
        assertEquals(jsonStr, GsonProvider.gsonBuilder().create().toJson(new TestDurationObject()));

        final var obj1 = GsonProvider.GSON.fromJson(jsonStr, TestDurationObject.class);
        assertNotNull(obj1);
        assertEquals(Duration.ofMinutes(30), obj1.duration);
        final var obj2 = GsonProvider.gsonBuilder().create().fromJson(jsonStr, TestDurationObject.class);
        assertNotNull(obj2);
        assertEquals(Duration.ofMinutes(30), obj2.duration);
    }
}