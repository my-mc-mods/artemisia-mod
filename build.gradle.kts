plugins {
    id("build-logic")
    id("java-library")
    id("maven-publish")
    alias(libs.plugins.shadow) apply false
}

subprojects {
    plugins.apply("java-library")
    plugins.apply("maven-publish")

    repositories {
        maven("https://api.modrinth.com/maven") {
            content { includeGroup("maven.modrinth") }
        }
        exclusiveContent {
            forRepository { maven("https://cursemaven.com") }
            filter { includeGroup("curse.maven") }
        }
        maven("https://jitpack.io")
    }

    dependencies {
        testImplementation(platform("org.junit:junit-bom:6.0.0"))
        testImplementation("org.junit.jupiter:junit-jupiter")
        testRuntimeOnly("org.junit.platform:junit-platform-launcher")

        compileOnly(rootProject.libs.lombok)
        annotationProcessor(rootProject.libs.lombok)
    }

    tasks {
        withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar>().configureEach {
            // semver4j
            relocate("org.semver4j", "${mod.group}.shadowed.semver4j")
            // luaj-jse
            relocate("org.luaj", "${mod.group}.shadowed.luaj")
            exclude("/lua*.class")
            // taffy
            relocate("dev.vfyjxf.taffy", "${mod.group}.shadowed.taffy")
            // css-parser
            relocate("com.osbcp.cssparser", "${mod.group}.shadowed.cssparser")
        }
    }

    publishing {
        repositories {
            maven {
                val snapshot = mod.version.endsWith("SNAPSHOT")
                System.getenv("MAVEN_RELEASES_URL")?.let { if (!snapshot) url = uri(it) }
                System.getenv("MAVEN_SNAPSHOTS_URL")?.let { if (snapshot) url = uri(it) }

                val mavenUsername = System.getenv("MAVEN_USERNAME")
                val mavenPassword = System.getenv("MAVEN_USERNAME")
                if (mavenUsername != null && mavenPassword != null) {
                    credentials(PasswordCredentials::class) {
                        username = mavenUsername; password = mavenPassword
                    }
                    authentication { create<BasicAuthentication>("basic") }
                }
            }
        }
    }
}