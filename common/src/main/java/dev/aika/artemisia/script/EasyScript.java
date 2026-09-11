package dev.aika.artemisia.script;

import org.luaj.vm2.LuaClosure;
import org.luaj.vm2.LuaValue;

@SuppressWarnings("unused")
public record EasyScript(byte[] bytecode) {
    public EasyScript(String code, boolean stripDebug) {
        this(ScriptHelper.compileCode(code, stripDebug));
    }

    public EasyScript(String code) {
        this(ScriptHelper.compileCode(code, false));
    }

    public static EasyScript expression(String code) {
        return new EasyScript("return " + code, true);
    }

    public LuaClosure closure(LuaValue env) {
        return new LuaClosure(ScriptHelper.loadBytecode(bytecode), env);
    }

    public LuaClosure closure() {
        return closure(ScriptHelper.createGlobals());
    }

    public LuaValue evaluate(LuaValue env) {
        return closure(env).call();
    }

    public LuaValue evaluate() {
        return closure().call();
    }
}