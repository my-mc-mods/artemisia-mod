@file:Suppress("AvoidDuplicateDependencies")

plugins {
    id("multiloader-neoforge")
}

repositories {
}

dependencies {
    implementation(libs.semver4j); shadowDep(libs.semver4j) { isTransitive = false }
    implementation(libs.luaj.jse); shadowDep(libs.luaj.jse)
    implementation(libs.taffy); shadowDep(libs.taffy) { isTransitive = false }
    implementation(libs.cssparser); shadowDep(libs.cssparser) { isTransitive = false }
}

sourceSets.main.get().resources { srcDir("src/generated/resources") }

operator fun String.invoke(): String = rootProject.ext[this] as? String ?: error("No property \"$this\"")