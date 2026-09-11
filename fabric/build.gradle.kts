@file:Suppress("SpellCheckingInspection", "AvoidDuplicateDependencies")

plugins {
    id("multiloader-fabric")
}

repositories {
    maven("https://maven.terraformersmc.com/") { name = "Terraformers" }
}

dependencies {
    localRuntime(libs.fabric.api)
    compileOnly(libs.fabric.modmenu)
    localRuntime(libs.fabric.modmenu) { isTransitive = false }

    implementation(libs.semver4j) {
        shadowDep(copy()) { isTransitive = false }
    }
    implementation(libs.luaj.jse) { shadowDep(copy()) }
    implementation(libs.taffy) {
        shadowDep(copy()) { isTransitive = false }
    }
    implementation(libs.cssparser) {
        shadowDep(copy()) { isTransitive = false }
    }
}

operator fun String.invoke(): String = rootProject.ext[this] as? String ?: error("No property \"$this\"")