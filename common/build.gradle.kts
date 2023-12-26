plugins {
    alias(libs.plugins.architectury.loom)
}

base {
    archivesName.set("the-config-lib")
}

architectury {
    val enabledLoaders = rootProject.properties["loaders"].toString().split(",").map { it.trim() }
    common(enabledLoaders)
}

loom {
    accessWidenerPath = file("src/main/resources/the-config-lib.accesswidener")
}

dependencies {
    modImplementation(libs.fabric.loader)

    implementation(libs.bundles.twelvemonkeys.imageio)
    implementation(libs.bundles.parsers)
}

java {
    withSourcesJar()
}

tasks {
    remapJar {
        archiveClassifier.set(null as String?)

        from(rootProject.file("LICENSE"))
    }
}

publishing {
    publications {
        create<MavenPublication>("common") {
            artifactId = base.archivesName.map { "$it-common" }.get()

            from(components["java"])
        }
    }
}

tasks.findByPath("publishCommonPublicationToReleasesRepository")?.let {
    rootProject.tasks["releaseMod"].dependsOn(it)
}
