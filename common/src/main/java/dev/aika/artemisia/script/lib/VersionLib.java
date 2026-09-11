package dev.aika.artemisia.script.lib;

import dev.aika.artemisia.helper.VersionHelper;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.TwoArgFunction;

public class VersionLib extends TwoArgFunction {
    public VersionLib() {
    }

    @Override
    public LuaValue call(LuaValue name, LuaValue env) {
        LuaTable version = new LuaTable(0, 32);
        version.set("satisfies", VERSION_SATISFIES);

        env.set("version", version);
        env.get("package").get("loaded").set("version", version);
        return version;
    }

    static final LuaValue VERSION_SATISFIES = new TwoArgFunction() {
        @Override
        public LuaValue call(LuaValue version, LuaValue versionRange) {
            return LuaValue.valueOf(VersionHelper.get().satisfies(
                    version.tojstring(), versionRange.tojstring()
            ));
        }
    };
}