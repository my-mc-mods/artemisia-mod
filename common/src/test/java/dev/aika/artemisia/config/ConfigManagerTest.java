package dev.aika.artemisia.config;

import dev.aika.artemisia.Constants;
import dev.aika.artemisia.config.annotations.Config;
import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.awt.*;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

class ConfigManagerTest {
    @Test
    void testConfig(@TempDir Path tempDir) throws IOException {
        final var manager = ConfigManager.create(TestObject::new, tempDir).load();
        assertEquals(Color.RED, manager.get().color);
        assertEquals(Duration.ofMinutes(30), manager.get().duration);
        manager.get().setColor(Color.BLUE);
        manager.get().setDuration(Duration.ofSeconds(10));
        manager.save();
        try (var reader = new FileReader(manager.getFile())) {
            Constants.LOG.info("\n{}", reader.readAllAsString());
        }
    }

    @Config("test")
    @Getter @Setter
    static class TestObject {
        Color color = Color.RED;
        Duration duration = Duration.ofMinutes(30);
    }
}