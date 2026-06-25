@file:Suppress("AvoidDuplicateDependencies", "UnstableApiUsage")

plugins {
    id("mod-platform")
}

repositories {
    maven("https://maven.neoforged.net/releases") {
        content { includeGroupAndSubgroups("net.neoforged") }
    }
}

dependencies {
    minecraft("com.mojang:minecraft:${libs.versions.minecraft.get()}")
    @Suppress("USELESS_IS_CHECK")
    if (libs.neoforge is Provider<*>) neoForge(libs.create("neoforge")) else neoForge(libs.neoforge)

    localRuntime(libs.neoforge.bettermodlist)

    compileOnly(libs.evalex.get())?.let { shadowCommon("$it") }
}