plugins {
    id("java-library")
    id("mod-licenses")
    id("com.gradleup.shadow")
}

val libs = the<org.gradle.accessors.dm.LibrariesForLibs>()
base.archivesName.set("${mod.id}-${project.name}")

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(mod.javaVersion))
    withSourcesJar()
}

tasks {
    val copyLicenses = extra.get("copyLicenses") as CopySpec
    listOf("shadowJar", "sourcesJar").forEach {
        named<Jar>(it) {
            duplicatesStrategy = DuplicatesStrategy.INCLUDE
            with(copyLicenses)
        }
    }

    withType<ProcessResources> {
        val expandProps = mapOf(
            "version" to mod.version,
            "group" to mod.group,
            "license" to mod.license,
            "mod_id" to mod.id,
            "mod_name" to mod.name,
        )

        inputs.properties(expandProps)
        filesMatching(listOf("pack.mcmeta")) {
            expand(expandProps)
        }
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
                "Built-On-Minecraft" to libs.versions.minecraft.get()
            )
        )
    }
}