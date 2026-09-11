@file:Suppress("SpellCheckingInspection")

plugins {
    id("multiloader-platform")
    id("net.neoforged.moddev")
}

val libs = the<org.gradle.accessors.dm.LibrariesForLibs>()

val localRuntime = configurations.create("localRuntime").apply {
    isCanBeResolved = true
    isCanBeConsumed = false
}
configurations.runtimeClasspath.get().extendsFrom(localRuntime)

dependencies {
}

neoForge {
    version = libs.versions.neoforge.get()

    // Automatically enable neoforge AccessTransformers if the file exists
    project(":common").file("src/main/resources/META-INF/accesstransformer.cfg").let {
        if (it.exists()) accessTransformers.from(it.absolutePath)
    }
    runs {
        configureEach {
            systemProperty("neoforge.enabledGameTestNamespaces", mod.id)
            // Unify the run config names with fabric
            ideName = "NeoForge ${name.replaceFirstChar { it.titlecase() }} (${path})"
        }

        maybeCreate("client").apply {
            client()
            gameDirectory = mkdir(file("runs/client"))
        }
        maybeCreate("server").apply {
            server()
            file("runs/server").mkdirs()
            gameDirectory = mkdir(file("runs/server"))
        }

        configureEach {
            loggingConfigFile = rootProject.file("build-logic/src/main/resources/log4j2.xml")
        }
    }
    mods {
        maybeCreate(mod.id).apply { sourceSet(sourceSets.main.get()) }
    }
}

tasks {
    shadowJar { dependsOn(tasks.jar) }

    processResources { exclude("${mod.id}.accesswidener") }
}

operator fun String.invoke(): String = rootProject.ext[this] as? String ?: error("No property \"$this\"")