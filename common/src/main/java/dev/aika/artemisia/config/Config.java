package dev.aika.artemisia.config;

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

    String defaultCategory() default "general";

    @MagicConstant(stringValues = {ConfigType.COMMON, ConfigType.SERVER, ConfigType.CLIENT})
    String type() default ConfigType.COMMON;

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    @interface Category {
        String value();
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    @interface SubCategory {
        String value();
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    @interface RequiresRestart {
        boolean value() default true;
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    @interface Ignored {
        boolean value() default true;
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    @interface MixinList {
        Category value();

        String prefix() default "";
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    @interface Constraint {
        String value();
    }

//    @Retention(RetentionPolicy.RUNTIME)
//    @Target(ElementType.FIELD) //
//    @interface Syncing {
//    }
}