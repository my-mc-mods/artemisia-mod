package dev.aika.artemisia.script;

import org.junit.jupiter.api.Test;
import org.luaj.vm2.LuaClosure;
import org.luaj.vm2.LuaValue;

import java.util.function.BiFunction;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class ScriptHelperTest {
    @Test
    public void testCompileCode() {
        final var code = """
                function average(...)
                   result = 0
                   local arg={...}
                   for i,v in ipairs(arg) do
                      result = result + v
                   end
                   return result/select("#",...)
                end
                """;
        final byte[] bytecode = ScriptHelper.compileCode(code);
        final var prototype = ScriptHelper.loadBytecode(bytecode);
        final var globals = ScriptHelper.createGlobals();
        new LuaClosure(prototype, globals).call();

        final var average = globals.get("average");
        final var result = average.invoke(Stream.of(10, 5, 3, 4, 5, 6)
                .map(LuaValue::valueOf).toArray(LuaValue[]::new));
        assertEquals(5.5, result.todouble(1));
    }

    @Test
    public void testModLib() {
        final var code = "return mod.mc_version";
        final byte[] bytecode = ScriptHelper.compileCode(code);
        final var prototype = ScriptHelper.loadBytecode(bytecode);
        final var result = new LuaClosure(prototype, ScriptHelper.createGlobals()).call();
        assertEquals("26.1.2", result.tojstring());
    }

    @Test
    public void testVersionLib() {
        final var code = "return version.satisfies(value, range)";
        final byte[] bytecode = ScriptHelper.compileCode(code);
        final var prototype = ScriptHelper.loadBytecode(bytecode);
        final var globals = ScriptHelper.createGlobals();

        BiFunction<String, String, Boolean> test = (value, range) -> {
            globals.set("value", LuaValue.valueOf(value));
            globals.set("range", LuaValue.valueOf(range));
            return new LuaClosure(prototype, globals).call().toboolean();
        };

        assertTrue(test.apply("1.20.1", ">=1.20 <1.20.2"));
        assertFalse(test.apply("1.20.1", ">=1.20 <1.20.1"));
        assertTrue(test.apply("26.1.2", ">=1.20 <26.2"));
        assertFalse(test.apply("26.1.2", ">=1.20 <26.1"));
    }
}