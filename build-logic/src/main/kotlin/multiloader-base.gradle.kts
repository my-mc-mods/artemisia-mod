plugins {
    id("java-library")
    id("maven-publish")
    id("com.gradleup.shadow")
}

val libs = the<org.gradle.accessors.dm.LibrariesForLibs>()
val mcVersion = project.property("minecraft_version")

group = mod.group
version = mod.version

base {
    archivesName = "${mod.id}-${project.name}"
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(project.property("java_version") as String))
    withSourcesJar()
}

@Suppress("UnstableApiUsage")
repositories {
    mavenCentral()

    exclusiveContent {
        forRepository { maven("https://repo.spongepowered.org/repository/maven-public") }
        filter { includeGroupAndSubgroups("org.spongepowered") }
    }
}

tasks {
    withType<Jar>().configureEach {
        duplicatesStrategy = DuplicatesStrategy.INCLUDE
    }

    named<Jar>("shadowJar") {
        listOf("LICENSE", "NOTICE", "LICENSE.txt", "NOTICE.txt").forEach { p ->
            from(rootProject.file(p)) {
                rename { "${mod.name}-${it}" }
            }
            from(rootProject.file(kotlin.io.path.Path(".custom", p))) {
                rename { "${mod.name}-${it}" }
            }
        }
    }

    withType<ProcessResources>().configureEach {
        val expandProps = mapOf(
            "version" to mod.version,
            "group" to mod.group,
            "license" to mod.license,
            "description" to mod.description,
            "mod_id" to mod.id,
            "mod_name" to mod.name,
            "mod_authors" to mod.authors.joinToString(", "),
            "mod_contributors" to mod.contributors.joinToString(", "),
            "fabric_minecraft_version_range" to project.property("fabric_minecraft_version_range") as String,
            "neoforge_minecraft_version_range" to project.property("neoforge_minecraft_version_range") as String,
        )

        filesMatching(listOf("META-INF/mods.toml", "META-INF/neoforge.mods.toml")) {
            expand(expandProps)
        }

        filesMatching(listOf("pack.mcmeta", "fabric.mod.json")) {
            expand(expandProps)
        }

        inputs.properties(expandProps)
    }

    jar {
        manifest.attributes(
            mapOf(
                "Specification-Title" to mod.name,
                "Specification-Vendor" to mod.authors.joinToString(", "),
                "Specification-Version" to "1",
                "Implementation-Title" to project.base.archivesName.get(),
                "Implementation-Version" to project.version,
                "Implementation-Vendor" to mod.authors.joinToString(", "),
                "Built-On-Minecraft" to mcVersion
            )
        )
    }

    tasks.named("clean") {
        doLast { delete("logs") }
    }
}

// Declare capabilities on the outgoing configurations.
// Read more about capabilities here: https://docs.gradle.org/current/userguide/component_capabilities.html#sec:declaring-additional-capabilities-for-a-local-component
listOf("apiElements", "runtimeElements", "sourcesElements").forEach {
    configurations.named(it).configure {
        outgoing {
            capability("${mod.group}:${mod.id}-${project.name}:${mod.version}")
            capability("${mod.group}:${mod.id}:${mod.version}")
        }
    }
    // Suppress Gradle metadata warnings for each published variant (only if publishing is configured)
    extensions.findByType<PublishingExtension>()?.let { publishing ->
        publishing.publications
            .withType(MavenPublication::class.java)
            .configureEach {
                suppressPomMetadataWarningsFor(it)
            }
    }
}

listOf(configurations.apiElements, configurations.runtimeElements).forEach {
    it.configure {
        outgoing.artifacts.clear()
        outgoing.artifact(tasks.shadowJar)
    }
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            artifactId = base.archivesName.get()
            version = if (mod.version.endsWith("-SNAPSHOT"))
                "${mod.version.removeSuffix("-SNAPSHOT")}-$mcVersion-SNAPSHOT"
            else "${mod.version}-$mcVersion"

            from(components["java"])
        }
    }
}