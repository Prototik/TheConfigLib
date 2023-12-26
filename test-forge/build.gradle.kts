plugins {
    alias(libs.plugins.architectury.loom)
}

base {
    archivesName = project(":test-common").base.archivesName
}

architectury {
    platformSetupLoomIde()
    forge()
}

loom {
    accessWidenerPath = project(":common").loom.accessWidenerPath

    mods {
        register("forge") {
            sourceSet("main", project(":forge"))
        }
    }
}

val common: Configuration by configurations.dependencyScope("common")

configurations {
    compileClasspath { extendsFrom(common) }
    runtimeClasspath { extendsFrom(common) }
    "developmentForge" { extendsFrom(common) }
}

dependencies {
    forge(libs.forge)

    setOf(libs.bundles.twelvemonkeys.imageio, libs.bundles.parsers).forEach {
        implementation(it)
        forgeRuntimeLibrary(it)
    }

    common(project(path = ":test-common", configuration = "namedElements")) { isTransitive = false }
    common(project(path = ":common", configuration = "namedElements")) { isTransitive = false }
    implementation(project(path = ":forge", configuration = "namedElements"))
}
