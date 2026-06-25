plugins {
    id("java")
    id("build-logic")
    alias(libs.plugins.shadow) apply false
    alias(libs.plugins.architectury.plugin)
    alias(libs.plugins.architectury.loom) apply false
}

architectury { minecraft = libs.versions.minecraft.get() }

subprojects {
    val libs = rootProject.libs

    plugins.apply(libs.plugins.architectury.loom.get().pluginId)

    repositories {
        maven("https://api.modrinth.com/maven") {
            content { includeGroup("maven.modrinth") }
        }
        maven("https://cursemaven.com") {
            content { includeGroup("curse.maven") }
        }
        maven("https://jitpack.io")
    }

    dependencies {
        compileOnly(libs.lombok)
        annotationProcessor(libs.lombok)
    }

    tasks.withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar>().configureEach {
        relocate("com.ezylang.evalex", "${mod.group}.shadowed.evalex")
        exclude("META-INF/proguard/**/*")
        relocate("org.apache.maven", "${mod.group}.shadowed.maven")
        exclude("META-INF/DEPENDENCIES", "META-INF/NOTICE", "META-INF/LICENSE")
    }
}

allprojects {
    plugins.apply("java")

    group = mod.group
    version = mod.version

    base.archivesName.set("${mod.id}-${project.name}")

    tasks.withType<JavaCompile>().configureEach {
        options.encoding = "UTF-8"
        options.release.set(mod.javaVersion)
    }
}