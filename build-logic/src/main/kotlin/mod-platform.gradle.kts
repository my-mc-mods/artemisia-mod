import org.slf4j.event.Level

plugins {
    id("maven-publish")
    id("mod-base")
    id("architectury-plugin")
    id("dev.architectury.loom-no-remap")
}

architectury {
    platformSetupLoomIde()
    loader(project.name)
}

val platformName = loom.platform.get().displayName()
val common = configurations.create("common")
val shadowCommon = configurations.create("shadowCommon")
configurations {
    compileClasspath { extendsFrom(common) }
    runtimeClasspath { extendsFrom(common) }
    configurations.getByName("development$platformName").extendsFrom(common)
}

dependencies {
    common(project(path = ":common")) { isTransitive = false }
    shadowCommon(project(path = ":common", configuration = "transformProduction$platformName")) {
        isTransitive = false
    }
}

loom {
    runs {
        configureEach {
            log4jConfigs.from(rootProject.file("build-logic/src/main/resources/log4j2.xml"))
            vmArgs(
                "-Dfabric.log.level=${project.findProperty("debug.log.level")?.toString() ?: Level.INFO}",
                "-Dfabric.log.disableAnsi=false"
            )
        }

        getByName("client").programArgs.addAll(listOf("--username", "DevPlayer"))
    }
}

tasks {
    val copyThirdPartyLicenses = extra.get("copyThirdPartyLicenses") as CopySpec
    val generatePlatformMetadata = tasks.register("generatePlatformMetadata") {
        description = "Generates platform-specific mod metadata (Fabric / Forge / NeoForge)"

        val platforms = mapOf(
            "fabric" to PlatformMeta(
                input = "src/main/resources/fabric.mod.json",
                output = "fabric.mod.json",
                generator = { ctx -> generateFabricMetadata(ctx) }
            ),
            "neoforge" to PlatformMeta(
                input = "src/main/resources/META-INF/neoforge.mods.toml",
                output = "META-INF/neoforge.mods.toml",
                generator = { ctx -> generateNeoForgeMetadata(ctx) }
            ),
            "forge" to PlatformMeta(
                input = "src/main/resources/META-INF/mods.toml",
                output = "META-INF/mods.toml",
                generator = { ctx -> generateNeoForgeMetadata(ctx) }
            )
        )
        val outputDir = layout.buildDirectory.dir("generated/platform-resources")
        outputs.dir(outputDir)
        platforms[project.name]?.let { meta -> inputs.file(project.file(meta.input)) }
        val gen = outputDir.map { dir ->
            val meta = platforms[project.name] ?: return@map
            meta.generator(
                GenerationContext(
                    project.file(meta.input),
                    dir.file(meta.output).asFile,
                )
            )
        }
        doLast { gen.get() }
    }

    sourceSets.main.configure { resources.srcDir(generatePlatformMetadata) }

    jar { archiveClassifier.set("raw") }

    java { withSourcesJar() }

    shadowJar {
        dependsOn(jar)
        with(copyThirdPartyLicenses)

        exclude("META-INF/maven/**/*", "META-INF/versions/**/*")

        archiveClassifier = null
        configurations.set(listOf(shadowCommon))
    }

    processResources {
        duplicatesStrategy = DuplicatesStrategy.INCLUDE

        dependsOn(generatePlatformMetadata)

        from(rootProject.file("assets/logo.png")) { rename { "${mod.id}_logo.png" } }
        from(rootProject.file("assets/custom/logo.png")) { rename { "${mod.id}_logo.png" } }
    }
}

configurations {
    apiElements {
        outgoing.artifacts.clear()
        outgoing.artifact(tasks.shadowJar)
    }
    runtimeElements {
        outgoing.artifacts.clear()
        outgoing.artifact(tasks.shadowJar)
    }
}

publishing.publications {
    create<MavenPublication>("maven$platformName") {
        artifactId = tasks.jar.get().archiveBaseName.get()

        from(components["java"])
    }
}