@file:Suppress("SpellCheckingInspection")

plugins {
    id("java")
    id("net.neoforged.moddev")
}

group = "dev.aika.artemisia.example"
version = "0.1.0"

neoForge {
    version = libs.versions.neoforge.get()
}

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:6.0.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    implementation(project(":neoforge"))
}

tasks.processResources {
    from(rootProject.file("assets/logo.png")) { rename { "example_logo.png" } }
}

neoForge {
    runs {
        create("client") {
            client()

            ideName = "ExampleMod Client"
            systemProperty("neoforge.enabledGameTestNamespaces", mod.id)
        }

        configureEach {
            loggingConfigFile = rootProject.file("build-logic/src/main/resources/log4j2.xml")
        }
    }

    mods {
        create("examplemod") { sourceSet((sourceSets.main.get())) }
    }
}