import org.gradle.jvm.tasks.Jar

plugins {
    alias(libs.plugins.architectury.loom)
    alias(libs.plugins.shadow)
    alias(libs.plugins.minotaur)
    alias(libs.plugins.cursegradle)
}

architectury {
    platformSetupLoomIde()
    forge()
}

loom {
    accessWidenerPath = project(":common").loom.accessWidenerPath

    forge {
        mixinConfig("the-config-lib-common.mixins.json")
    }
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
    "developmentForge" { extendsFrom(common) }
}

val minecraftVersion: String = libs.versions.minecraft.get()

dependencies {
    forge(libs.forge)

    setOf(libs.bundles.twelvemonkeys.imageio, libs.bundles.parsers).forEach {
        compileOnly(it)
        forgeRuntimeLibrary(it)
        shadowCommon(it)
    }

    common(project(path = ":common", configuration = "namedElements")) { isTransitive = false }
    shadowCommon(project(path = ":common", configuration = "transformProductionForge")) { isTransitive = false }
}

java {
    withSourcesJar()
}

tasks {
    processResources {
        val modId: String by rootProject
        val modName: String by rootProject
        val modDescription: String by rootProject
        val githubProject: String by rootProject

        val props = mapOf(
            "id" to modId,
            "group" to project.group,
            "name" to modName,
            "description" to modDescription,
            "version" to project.version,
            "github" to githubProject,
        )

        inputs.properties(props)

        filesMatching(listOf("META-INF/mods.toml", "pack.mcmeta")) {
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
        atAccessWideners.add(loom.accessWidenerPath.map { it.asFile.name })

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
        versionName.set("${project.version} (Forge)")
        versionNumber.set("${project.version}-forge")
        versionType.set(if (isBeta) "beta" else "release")
        uploadFile.set(tasks["remapJar"])
        additionalFiles.add(tasks["remapSourcesJar"])
        gameVersions.set(listOf("1.20.4", "1.20.3"))
        loaders.set(listOf("forge"))
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
                displayName = "[Forge] ${project.version}"
            })
            addArtifact(tasks["remapSourcesJar"], closureOf<me.hypherionmc.cursegradle.CurseArtifact> {
                displayName = "[Forge] ${project.version} (sources)"
            })

            id = curseforgeId
            releaseType = if (isBeta) "beta" else "release"
            addGameVersion("1.20.4")
            addGameVersion("1.20.3")
            addGameVersion("Forge")
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
        create<MavenPublication>("forge") {
            artifactId = base.archivesName.map { "$it-${project.name}" }.get()

            from(components["java"])
        }
    }
}

tasks.findByPath("publishForgePublicationToReleasesRepository")?.let {
    rootProject.tasks["releaseMod"].dependsOn(it)
}
