plugins {
    `kotlin-dsl`
}

@Suppress("UnstableApiUsage")
repositories {
    mavenCentral()
    gradlePluginPortal()

    exclusiveContent {
        forRepository { maven("https://maven.fabricmc.net") }
        filter { includeGroupAndSubgroups("net.fabricmc") }
    }
}

dependencies {
    // https://github.com/gradle/gradle/issues/15383#issuecomment-779893192
    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))

    implementation("tools.jackson.core:jackson-core:3.1.2")
    implementation("tools.jackson.core:jackson-databind:3.1.2")
    implementation("io.hotmoka:toml4j:0.7.3")

    fun implementationPlugin(dep: String, version: String) = implementation("$dep:$dep.gradle.plugin:$version")
    implementationPlugin("net.fabricmc.fabric-loom-remap", libs.versions.fabric.loom.get())
    implementationPlugin("net.neoforged.moddev", libs.versions.moddev.get())
    implementationPlugin("com.gradleup.shadow", libs.versions.shadow.get())
}