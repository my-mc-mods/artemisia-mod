package dev.aika.artemisia.mod;

import dev.aika.artemisia.config.json.GsonProvider;
import dev.aika.artemisia.script.ScriptHelper;
import dev.aika.artemisia.script.ScriptManager;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.experimental.Accessors;
import org.intellij.lang.annotations.Language;
import org.luaj.vm2.LuaValue;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

@SuppressWarnings("unused")
@Accessors(chain = true)
public class ModMixinManager {
    @Getter
    private final Set<Entry> mixins = new LinkedHashSet<>();
    private final Supplier<Object> configSupplier;
    private final ScriptManager scriptManager;
    @Setter
    private Supplier<Set<String>> disabledMixinsSupplier;

    private ModMixinManager(ScriptManager scriptManager, Supplier<Object> configSupplier) {
        this.configSupplier = configSupplier;
        this.scriptManager = scriptManager;
    }

    public static ModMixinManager create(ScriptManager scriptManager, Supplier<Object> configSupplier) {
        return new ModMixinManager(scriptManager, configSupplier);
    }

    public void add(@Language(value = "JAVA", suffix = ".class") String mixin, String expression) {
        mixins.add(new Entry(mixin, expression));
    }

    public void add(@Language(value = "JAVA", suffix = ".class") String mixin) {
        add(mixin, null);
    }

    @SuppressWarnings("unused")
    public boolean shouldApply(String mixinClassName) {
        if (disabledMixinsSupplier != null && disabledMixinsSupplier.get().contains(mixinClassName))
            return false;
        return mixins.stream().filter(it -> it.mixin.equals(mixinClassName)).findFirst()
                .orElseThrow(() -> new RuntimeException("Unknown mixin class: " + mixinClassName))
                .shouldApply();
    }

    @Accessors(fluent = true, chain = true)
    @Getter
    public class Entry {
        private final String mixin;
        private final String expression;

        private Entry(String mixin, String expression) {
            this.mixin = mixin;
            this.expression = expression;
        }

        @SneakyThrows
        public boolean shouldApply() {
            if (expression == null || expression.isEmpty()) return true;
            final LuaValue env = ScriptHelper.createGlobals();
            env.set("config", ScriptHelper.map2LuaValue(GsonProvider.GSON.fromJson(
                    GsonProvider.GSON.toJsonTree(ModMixinManager.this.configSupplier.get()),
                    Map.class
            )));
            final var result = scriptManager.getExpression(expression).evaluate(env);
            return result.isboolean() && result.toboolean();
        }
    }
}