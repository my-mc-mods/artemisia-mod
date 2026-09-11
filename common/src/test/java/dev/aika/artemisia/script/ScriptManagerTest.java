package dev.aika.artemisia.script;

import dev.aika.artemisia.mod.ModPlatform;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ScriptManagerTest {
    @Test
    public void testModLib() {
        final var manager = new ScriptManager(false);
        assertEquals("26.1.2", manager.getExpression("mod.mc_version").evaluate().tojstring());
        assertFalse(manager.getExpression("mod.loaded(\"asd\")").evaluate().toboolean());
        assertEquals(ModPlatform.FABRIC, manager.getExpression("mod.platform.name").evaluate().tojstring());
        assertTrue(manager.getExpression("mod.platform.devenv").evaluate().toboolean());
    }
}