plugins {
    id("multiloader-base")
}

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
val shadowDep = configurations.maybeCreate("shadowDep").apply {
    isCanBeResolved = true
    isCanBeConsumed = true
}
val commonJava = configurations.maybeCreate("commonJava").apply {
    isCanBeResolved = true
}
val commonResources = configurations.maybeCreate("commonResources").apply {
    isCanBeResolved = true
}

dependencies {
    compileOnly(project(":common")) {
        capabilities {
            requireCapability("$group:${mod.id}")
        }
    }
    commonJava(project(path = ":common", configuration = "commonJava"))
    commonResources(project(path = ":common", configuration = "commonResources"))
}

tasks {
    jar { archiveClassifier = "slim" }

    named<JavaCompile>("compileJava") {
        dependsOn(commonJava)
        source(commonJava)
    }
    named<Jar>("sourcesJar") {
        dependsOn(commonJava)
        from(commonJava)
        dependsOn(commonResources)
        from(commonResources)
    }

    shadowJar {
        archiveClassifier = null
        configurations.set(listOf(shadowDep))

        listOf(
            project.file("third-party-licenses"),
            rootProject.file("third-party-licenses"),
            project(":common").file("third-party-licenses")
        ).forEach {
            from(it) {
                into("META-INF/licenses")
                exclude("**/.gitkeep")
            }
        }

        exclude("META-INF/maven/**/*", "META-INF/versions/**/*")

        mergeServiceFiles()
    }

    sourceSets.main.configure { resources.srcDir(generatePlatformMetadata) }

    processResources {
        duplicatesStrategy = DuplicatesStrategy.INCLUDE

        dependsOn(commonResources)
        from(commonResources)
        dependsOn(generatePlatformMetadata)

        from(rootProject.file("assets/logo.png")) { rename { "${mod.id}_logo.png" } }
        from(rootProject.file(".custom/assets/logo.png")) { rename { "${mod.id}_logo.png" } }
    }
}