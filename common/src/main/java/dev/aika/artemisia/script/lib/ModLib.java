package dev.aika.artemisia.script.lib;

import dev.aika.artemisia.platform.PlatformHelper;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;
import org.luaj.vm2.lib.TwoArgFunction;

public class ModLib extends TwoArgFunction {
    public ModLib() {
    }

    @Override
    public LuaValue call(LuaValue name, LuaValue env) {
        LuaTable platform = new LuaTable(0, 32);
        platform.set("name", LuaValue.valueOf(PlatformHelper.get().getPlatformName()));
        platform.set("devenv", LuaValue.valueOf(PlatformHelper.get().isDevelopmentEnvironment()));
        platform.set("envtype", LuaValue.valueOf(PlatformHelper.get().getEnvType().name()));

        LuaTable mod = new LuaTable(0, 32);
        mod.set("platform", platform);
        mod.set("loaded", IS_MOD_LOADED);
        mod.set("mc_version", LuaValue.valueOf(PlatformHelper.get().getMinecraftVersion()));

        env.set("mod", mod);
        env.get("package").get("loaded").set("mod", mod);
        return null;
    }

    static final LuaValue IS_MOD_LOADED = new OneArgFunction() {
        @Override
        public LuaValue call(LuaValue arg) {
            String modId = arg.checkjstring();
            return LuaValue.valueOf(PlatformHelper.get().isModLoaded(modId));
        }
    };
}