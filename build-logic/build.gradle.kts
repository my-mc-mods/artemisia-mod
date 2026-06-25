plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
    gradlePluginPortal()
    maven("https://maven.fabricmc.net/")
    maven("https://maven.architectury.dev/")
    maven("https://files.minecraftforge.net/maven/")
}

dependencies {
    // https://github.com/gradle/gradle/issues/15383#issuecomment-779893192
    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))

    implementation("tools.jackson.core:jackson-core:3.1.2")
    implementation("tools.jackson.core:jackson-databind:3.1.2")
    implementation("io.hotmoka:toml4j:0.7.3")

    implementation(
        "dev.architectury.loom-no-remap:dev.architectury.loom-no-remap.gradle.plugin:${libs.versions.architectury.loom.get()}"
    )
    implementation("architectury-plugin:architectury-plugin.gradle.plugin:${libs.versions.architectury.plugin.get()}")
    implementation("com.gradleup.shadow:com.gradleup.shadow.gradle.plugin:${libs.versions.shadow.get()}")
}