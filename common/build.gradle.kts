@file:Suppress("AvoidDuplicateDependencies")

plugins {
    id("mod-common")
}

loom {
    val aw = file("src/main/resources/${mod.id}.accesswidener")
    if (aw.exists()) accessWidenerPath.set(aw)
}

dependencies {
    minecraft("com.mojang:minecraft:${libs.versions.minecraft.get()}")
    implementation(libs.fabric.loader)

    testImplementation(platform("org.junit:junit-bom:6.0.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testCompileOnly(libs.lombok)
    testAnnotationProcessor(libs.lombok)

    implementation(libs.evalex.get())?.let { shadowCommon("$it") }
    implementation(libs.maven.artifact.get())?.let {
        shadowCommon("$it") { exclude(group = "org.codehaus.plexus") }
    }
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
}