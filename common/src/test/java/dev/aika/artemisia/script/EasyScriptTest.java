package dev.aika.artemisia.script;

import org.junit.jupiter.api.Test;
import org.luaj.vm2.LuaValue;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EasyScriptTest {
    @Test
    public void test() {
        final var globals = ScriptHelper.createGlobals();
        globals.set("arg", LuaValue.valueOf(13));
        final var result = EasyScript.expression("arg / 2").evaluate(globals);
        assertTrue(result.isnumber());
        assertEquals(6.5, result.tonumber().tofloat());

        assertEquals("26.1.2", EasyScript.expression("mod.mc_version").evaluate().tojstring());
    }
}