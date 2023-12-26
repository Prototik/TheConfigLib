plugins {
    alias(libs.plugins.architectury.loom)
}

base {
    archivesName = project(":test-common").base.archivesName
}

architectury {
    platformSetupLoomIde()
    neoForge()
}

loom {
    accessWidenerPath = project(":common").loom.accessWidenerPath

    mods {
        register("neoforge") {
            sourceSet("main", project(":neoforge"))
        }
    }
}

val common: Configuration by configurations.dependencyScope("common")

configurations {
    compileClasspath { extendsFrom(common) }
    runtimeClasspath { extendsFrom(common) }
    "developmentNeoForge" { extendsFrom(common) }
}

dependencies {
    neoForge(libs.neoforge)

    setOf(libs.bundles.twelvemonkeys.imageio, libs.bundles.parsers).forEach {
        implementation(it)
        forgeRuntimeLibrary(it)
    }

    common(project(path = ":common", configuration = "namedElements")) { isTransitive = false }
    common(project(path = ":test-common", configuration = "namedElements")) { isTransitive = false }
    implementation(project(path = ":neoforge", configuration = "namedElements"))
}
