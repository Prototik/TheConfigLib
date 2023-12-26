plugins {
    alias(libs.plugins.architectury.loom)
}

base {
    archivesName = project(":test-common").base.archivesName
}

architectury {
    platformSetupLoomIde()
    fabric()
}

loom {
    accessWidenerPath = project(":common").loom.accessWidenerPath

    mods {
        register("fabric") {
            sourceSet("main", project(":fabric"))
        }
    }
}

val common: Configuration by configurations.dependencyScope("common")

configurations {
    compileClasspath { extendsFrom(common) }
    runtimeClasspath { extendsFrom(common) }
    "developmentFabric" { extendsFrom(common) }
}

repositories {
    exclusiveContent {
        forRepository {
            maven("https://maven.terraformersmc.com/releases") {
                name = "TerraformersMC"
            }
        }
        filter {
            includeGroupByRegex("^com.terraformersmc(\\..+)?$")
        }
    }
}

dependencies {
    modImplementation(libs.fabric.loader)

    setOf(libs.bundles.twelvemonkeys.imageio, libs.bundles.parsers).forEach {
        implementation(it)
    }

    common(project(path = ":common", configuration = "namedElements")) { isTransitive = false }
    common(project(path = ":test-common", configuration = "namedElements")) { isTransitive = false }
    implementation(project(path = ":fabric", configuration = "namedElements"))

    modCompileOnly(libs.modmenu)
    modLocalRuntime(libs.modmenu)
}
