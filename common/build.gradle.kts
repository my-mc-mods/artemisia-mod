@file:Suppress("AvoidDuplicateDependencies")

plugins {
    id("multiloader-common")
}

repositories {
}

dependencies {
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
    implementation(libs.diffutils) {
        shadowDep(copy()) { isTransitive = false }
    }
}

dependencies {
    testImplementation("org.slf4j:slf4j-api:2.0.17")
    testImplementation("org.apache.logging.log4j:log4j-slf4j2-impl:2.25.2")
    testCompileOnly("org.jetbrains:annotations:26.0.2")
    testImplementation("com.google.guava:guava:33.5.0-jre")
}
tasks.test {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
}

operator fun String.invoke(): String = rootProject.ext[this] as? String ?: error("No property \"$this\"")