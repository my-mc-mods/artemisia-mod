@file:Suppress("AvoidDuplicateDependencies")

plugins {
    id("mod-platform")
}

loom {
    accessWidenerPath = project(":common").loom.accessWidenerPath
}

repositories {
    maven("https://maven.terraformersmc.com/") { name = "Terraformers" }
}

dependencies {
    minecraft("com.mojang:minecraft:${libs.versions.minecraft.get()}")
    implementation(libs.fabric.loader)
    localRuntime(libs.fabric.modmenu)

    compileOnly(libs.evalex.get())?.let { shadowCommon("$it") }
    compileOnly(libs.maven.artifact.get())?.let {
        shadowCommon("$it") { exclude(group = "org.codehaus.plexus") }
    }
}