plugins {
    id("maven-publish")
    id("mod-base")
    id("architectury-plugin")
}

architectury { common(mod.platforms) }

val shadowCommon = configurations.create("shadowCommon")

tasks {
    jar { archiveClassifier.set("raw") }

    java { withSourcesJar() }

    shadowJar {
        dependsOn(jar)

        archiveClassifier = null
        configurations.set(listOf(shadowCommon))
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
    create<MavenPublication>("mavenCommon") {
        artifactId = tasks.jar.get().archiveBaseName.get()

        from(components["java"])
    }
}