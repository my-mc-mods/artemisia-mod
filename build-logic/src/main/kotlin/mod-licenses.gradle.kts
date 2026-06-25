ext.set("copyLicenses", copySpec {
    listOf("LICENSE", "NOTICE", "LICENSE.txt", "NOTICE.txt").forEach { n ->
        from(rootProject.file(n)) { into("") }
        from(rootProject.file("assets/custom/$n")) { rename { n }; into("") }
    }
})
ext.set("copyThirdPartyLicenses", copySpec {
    from(project.file("third-party-licenses")) {
        into("third-party-licenses")
        exclude("**/.gitkeep")
    }
    from(project(":common").file("third-party-licenses")) {
        into("third-party-licenses")
        exclude("**/.gitkeep")
    }
})