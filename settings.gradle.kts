@file:Suppress("UnstableApiUsage")

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        maven("https://maven.fabricmc.net/") {
            name = "Fabric"
            content { includeGroupAndSubgroups("net.fabricmc") }
        }
        maven("https://maven.architectury.dev/") { name = "Architectury" }
        maven("https://maven.neoforged.net/releases/") {
            name = "NeoForge"
            content {
                includeGroupAndSubgroups("de.oceanlabs")
                includeGroupAndSubgroups("net.minecraftforge")
            }
        }
    }
    includeBuild("build-logic")
}

rootProject.name = "artemisia"

include("common")
include("fabric")
include("neoforge")