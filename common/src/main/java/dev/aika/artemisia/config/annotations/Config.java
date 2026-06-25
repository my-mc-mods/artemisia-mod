package dev.aika.artemisia.config.annotations;

import dev.aika.artemisia.config.ConfigType;
import dev.aika.artemisia.config.ModPlatform;
import org.intellij.lang.annotations.MagicConstant;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@SuppressWarnings("unused")
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Config {
    String value();

    @MagicConstant(stringValues = {ConfigType.COMMON, ConfigType.SERVER, ConfigType.CLIENT})
    String type() default ConfigType.COMMON;

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD) //
    @interface Category {
        String value() default "general";
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD) //
    @interface RequiresRestart {
        boolean value() default true;
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD) //
    @interface Ignored {
        boolean value() default true;
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD) //
    @interface PlatformSpecific {
        @MagicConstant(stringValues = {ModPlatform.NEOFORGE, ModPlatform.FABRIC, ModPlatform.FORGE, ModPlatform.QUILT})
        String value();
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD) //
    @interface MixinList {
        Category value();

        String prefix() default "";
    }
}