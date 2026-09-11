plugins {
    id("multiloader-platform")
    id("net.fabricmc.fabric-loom")
}

val libs = the<org.gradle.accessors.dm.LibrariesForLibs>()

dependencies {
    minecraft("com.mojang:minecraft:${project.property("minecraft_version")}")
    api(libs.fabric.loader)
}

@Suppress("UnstableApiUsage")
loom {
    project(":common").file("src/main/resources/${mod.id}.accesswidener").let {
        if (it.exists()) accessWidenerPath.set(it)
    }
    mixin {
        useLegacyMixinAp = false
        defaultRefmapName.set("${mod.id}.refmap.json")
    }
    runs {
        all {
            generateRunConfig = true
            displayName.set("Fabric ${name.replaceFirstChar { it.uppercase() }}")
        }
        maybeCreate("client").apply {
            client()
            runDirectory = file("runs/client")
        }
        maybeCreate("server").apply {
            server()
            runDirectory = file("runs/server")
        }
    }
}

tasks {
    shadowJar { dependsOn(tasks.jar) }

    processResources { exclude("META-INF/accesstransformer.cfg") }
}