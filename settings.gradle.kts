pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
        maven("https://maven.fabricmc.net")
        maven("https://maven.architectury.dev/")
        maven("https://maven.neoforged.net/releases")
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}

dependencyResolutionManagement {
    versionCatalogs {
        create("libs")
    }
}

rootProject.name = "TheConfigLib"

val enabledLoaders = settings.extra.properties["loaders"].toString().split(",").map { it.trim() }

include("common")
include("test-common")

if ("fabric" in enabledLoaders) {
    include("fabric")
    include("test-fabric")
}

if ("forge" in enabledLoaders) {
    include("forge")
    include("test-forge")
}

if ("neoforge" in enabledLoaders) {
    include("neoforge")
    include("test-neoforge")
}
