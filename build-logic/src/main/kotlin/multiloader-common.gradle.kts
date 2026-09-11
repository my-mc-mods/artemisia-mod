plugins {
    id("multiloader-base")
    id("net.neoforged.moddev")
}

val libs = the<org.gradle.accessors.dm.LibrariesForLibs>()
val shadowDep = configurations.create("shadowDep").apply {
    isCanBeResolved = true
    isCanBeConsumed = true
}
val commonJava = configurations.create("commonJava").apply {
    isCanBeResolved = false
    isCanBeConsumed = true
}
val commonResources = configurations.create("commonResources").apply {
    isCanBeResolved = false
    isCanBeConsumed = true
}

neoForge {
    neoFormVersion = libs.versions.neoform.get()
    // Automatically enable AccessTransformers if the file exists
    file("src/main/resources/META-INF/accesstransformer.cfg").let {
        if (it.exists()) accessTransformers.from(it.absolutePath)
    }
}

dependencies {
    // Fabric and NeoForge both bundle Fabric Mixin, so it is safe to use it in common
    // If you need to update, check what version they are using to see what is compatible
    // https://github.com/neoforged/NeoForge/blob/26.1.x/gradle.properties#L37
    // https://github.com/FabricMC/fabric-loader/blob/master/gradle.properties#L12
    compileOnly("net.fabricmc:sponge-mixin:0.17.3+mixin.0.8.7")
    // Fabric and NeoForge both bundle MixinExtras, so it is safe to use it in common
    compileOnly(annotationProcessor("io.github.llamalad7:mixinextras-common:0.5.3")!!)
}

tasks {
    jar { archiveClassifier = "slim" }

    shadowJar {
        archiveClassifier = null
        configurations.set(listOf(shadowDep))

        listOf(project.file("third-party-licenses"), rootProject.file("third-party-licenses")).forEach {
            from(it) {
                into("META-INF/licenses")
                exclude("**/.gitkeep")
            }
        }

        exclude("META-INF/maven/**/*", "META-INF/versions/**/*")

        mergeServiceFiles()
    }
}

artifacts {
    add("commonJava", sourceSets.main.get().java.sourceDirectories.singleFile)
    add("commonResources", sourceSets.main.get().resources.sourceDirectories.singleFile)
}