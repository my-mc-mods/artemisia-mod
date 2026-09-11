package dev.aika.artemisia.script;

import com.google.common.collect.ObjectArrays;
import dev.aika.artemisia.script.lib.ModLib;
import dev.aika.artemisia.script.lib.VersionLib;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.luaj.vm2.*;
import org.luaj.vm2.compiler.DumpState;
import org.luaj.vm2.compiler.LuaC;
import org.luaj.vm2.lib.*;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;
import org.luaj.vm2.lib.jse.JseMathLib;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.StringReader;
import java.util.List;
import java.util.Map;

@UtilityClass
public class ScriptHelper {
    private static final Globals compileEnv = new Globals();
    public static final LuaValue[] BASE_LIBS = new LuaValue[]{
            new BaseLib(), new PackageLib(),
            new Bit32Lib(), new TableLib(), new StringLib(), new JseMathLib()};
    public static final LuaValue[] DEFAULT_LIBS = ObjectArrays.concat(BASE_LIBS, new LuaValue[]{
            new ModLib(), new VersionLib()
    }, LuaValue.class);

    static {
        LuaC.install(compileEnv);
    }

    public Globals createGlobals(LuaValue... libs) {
        final Globals globals = new Globals();

        for (LuaValue lib : libs) globals.load(lib);

        LoadState.install(globals);
        LuaC.install(globals);
        return globals;
    }

    public Globals createGlobals() {
        return createGlobals(DEFAULT_LIBS);
    }

    @SneakyThrows
    public byte[] compileCode(String code, String chunkName, boolean stripDebug) {
        final Prototype prototype = compileEnv.compilePrototype(new StringReader(code), chunkName);
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        DumpState.dump(prototype, out, stripDebug);
        return out.toByteArray();
    }

    public byte[] compileCode(String code, boolean stripDebug) {
        return compileCode(code, "script.lua", stripDebug);
    }

    public byte[] compileCode(String code) {
        return compileCode(code, "script.lua", false);
    }

    @SneakyThrows
    public Prototype loadBytecode(byte[] data, String chunkName) {
        return LoadState.instance.undump(new ByteArrayInputStream(data), chunkName);
    }

    public Prototype loadBytecode(byte[] data) {
        return loadBytecode(data, "script.lua");
    }

    public LuaValue map2LuaValue(Object value) {
        if (value == null) return LuaValue.NIL;

        if (value instanceof Map<?, ?> map) {
            LuaTable table = new LuaTable();
            for (var entry : map.entrySet()) {
                table.set(CoerceJavaToLua.coerce(entry.getKey()), map2LuaValue(entry.getValue()));
            }
            return table;
        } else if (value instanceof List<?> list) {
            LuaTable table = new LuaTable();
            for (int i = 0; i < list.size(); i++) {
                table.set(i + 1, map2LuaValue(list.get(i)));
            }
            return table;
        }
        return CoerceJavaToLua.coerce(value);
    }
}