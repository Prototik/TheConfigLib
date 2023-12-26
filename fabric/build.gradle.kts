import org.gradle.jvm.tasks.Jar

plugins {
    alias(libs.plugins.architectury.loom)
    alias(libs.plugins.shadow)
    alias(libs.plugins.minotaur)
    alias(libs.plugins.cursegradle)
}

architectury {
    platformSetupLoomIde()
    fabric()
}

loom {
    accessWidenerPath = project(":common").loom.accessWidenerPath
}

val common: Configuration by configurations.dependencyScope("common")
val shadowCommon: Configuration by configurations.dependencyScope("shadowCommon") {
    exclude(group = "com.google.code.gson", "gson")
    exclude(group = "org.jetbrains", "annotations")
}
val shadowCommonResolvable: Configuration by configurations.resolvable("shadowCommonResolvable") {
    extendsFrom(shadowCommon)
}

configurations {
    compileClasspath { extendsFrom(common) }
    runtimeClasspath { extendsFrom(common) }
    "developmentFabric" { extendsFrom(common) }
}

val minecraftVersion = libs.versions.minecraft.get()

dependencies {
    modImplementation(libs.fabric.loader)

    listOf(
        "fabric-resource-loader-v0",
    ).forEach { modApi(fabricApi.module(it, libs.versions.fabric.api.get())) }

    setOf(libs.bundles.twelvemonkeys.imageio, libs.bundles.parsers).forEach {
        compileOnly(it)
        localRuntime(it)
        shadowCommon(it)
    }

    common(project(path = ":common", configuration = "namedElements")) { isTransitive = false }
    shadowCommon(project(path = ":common", configuration = "transformProductionFabric")) { isTransitive = false }
}

java {
    withSourcesJar()
}

tasks {
    processResources {
        val modId: String by project
        val modName: String by project
        val modDescription: String by project
        val githubProject: String by project

        val props = mapOf(
            "id" to modId,
            "group" to project.group,
            "name" to modName,
            "description" to modDescription,
            "version" to project.version,
            "github" to githubProject,
        )

        inputs.properties(props)

        filesMatching("fabric.mod.json") {
            expand(props)
        }
    }

    shadowJar {
        exclude("architectury.common.json")
        exclude("META-INF/maven")

        relocate("com.twelvemonkeys", "dev.tcl.shadow.com.twelvemonkeys")
        relocate("org.quiltmc.parsers", "dev.tcl.shadow.org.quiltmc.parsers")
        mergeServiceFiles()

        configurations = listOf(shadowCommonResolvable)
        archiveClassifier = "dev-shadow"
    }

    remapJar {
        injectAccessWidener = true
        inputFile = shadowJar.flatMap { it.archiveFile }
        dependsOn(shadowJar)
        archiveClassifier = null
    }

    named<Jar>("sourcesJar") {
        archiveClassifier = "dev-sources"
        val commonSources = project(":common").tasks.named<Jar>("sourcesJar")
        dependsOn(commonSources)
        from(commonSources.flatMap { it.archiveFile }.map { zipTree(it) })
    }

    remapSourcesJar {
        archiveClassifier = "sources"
    }

    jar {
        archiveClassifier = "dev"
    }
}

components.named<AdhocComponentWithVariants>("java") {
    withVariantsFromConfiguration(configurations["shadowRuntimeElements"]) {
        skip()
    }
}

val changelogText: String by ext
val isBeta: Boolean by ext

val modrinthId: String by project
if (modrinthId.isNotEmpty()) {
    modrinth {
        token.set(findProperty("modrinth.token")?.toString())
        projectId.set(modrinthId)
        versionName.set("${project.version} (Fabric)")
        versionNumber.set("${project.version}-fabric")
        versionType.set(if (isBeta) "beta" else "release")
        uploadFile.set(tasks["remapJar"])
        additionalFiles.add(tasks["remapSourcesJar"])
        gameVersions.set(listOf("1.20.3", "1.20.4"))
        loaders.set(listOf("fabric", "quilt"))
        changelog.set(changelogText)
        syncBodyFrom.set(rootProject.file("README.md").readText())
    }
}
rootProject.tasks["releaseMod"].dependsOn(tasks["modrinth"])

val curseforgeId: String by project
if (hasProperty("curseforge.token") && curseforgeId.isNotEmpty()) {
    curseforge {
        apiKey = findProperty("curseforge.token")
        project(closureOf<me.hypherionmc.cursegradle.CurseProject> {
            mainArtifact(tasks["remapJar"], closureOf<me.hypherionmc.cursegradle.CurseArtifact> {
                displayName = "[Fabric] ${project.version}"
            })
            addArtifact(tasks["remapSourcesJar"], closureOf<me.hypherionmc.cursegradle.CurseArtifact> {
                displayName = "[Fabric] ${project.version} (sources)"
            })

            id = curseforgeId
            releaseType = if (isBeta) "beta" else "release"
            addGameVersion("1.20.3")
            addGameVersion("1.20.4")
            addGameVersion("Fabric")
            addGameVersion("Java 17")

            changelog = changelogText
            changelogType = "markdown"
        })

        options(closureOf<me.hypherionmc.cursegradle.Options> {
            forgeGradleIntegration = false
            fabricIntegration = false
        })
    }
}

rootProject.tasks["releaseMod"].dependsOn(tasks["curseforge"])

publishing {
    publications {
        create<MavenPublication>("fabric") {
            artifactId = base.archivesName.map { "$it-${project.name}" }.get()

            from(components["java"])
        }
    }
}

tasks.findByPath("publishFabricPublicationToReleasesRepository")?.let {
    rootProject.tasks["releaseMod"].dependsOn(it)
}
