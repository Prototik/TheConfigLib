import net.fabricmc.loom.api.LoomGradleExtensionAPI
import org.eclipse.jgit.internal.storage.file.FileRepository

buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath("org.eclipse.jgit:org.eclipse.jgit:6.8.0.202311291450-r")
    }
}

plugins {
    base
    alias(libs.plugins.architectury.plugin)
    alias(libs.plugins.architectury.loom) apply false

    alias(libs.plugins.minotaur) apply false
    alias(libs.plugins.cursegradle) apply false
    alias(libs.plugins.github.release)
}

architectury {
    minecraft = libs.versions.minecraft.get()
}

val branch: Provider<String> = provider {
    FileRepository(layout.projectDirectory.asFile).branch
}

val isBeta = "beta" in version.toString()
val changelogText = rootProject.file("changelogs/${project.version}.md").takeIf { it.exists() }?.readText() ?: "No changelog provided."

base {
    archivesName = "the-config-lib"
}

if ("TCL_SNAPSHOT" in System.getenv().keys) {
    version = "$version-SNAPSHOT"
}

subprojects {
    version = rootProject.version

    pluginManager.withPlugin("base") {
        base.archivesName.convention(rootProject.base.archivesName)
    }

    ext["changelogText"] = changelogText
    ext["isBeta"] = isBeta

    repositories {
        maven("https://maven.aur.rocks/releases") {
            name = "kkIncReleases"
            mavenContent {
                releasesOnly()
            }
        }
        maven("https://maven.aur.rocks/snapshots") {
            name = "kkIncSnapshots"
            mavenContent {
                snapshotsOnly()
            }
        }
        mavenCentral {
            mavenContent {
                excludeGroup("com.twelvemonkeys")
                excludeGroup("com.twelvemonkeys.common")
                excludeGroup("com.twelvemonkeys.imageio")
            }
        }
        maven("https://maven.neoforged.net/releases") {
            name = "NeoForged"
        }
        exclusiveContent {
            forRepository {
                maven("https://api.modrinth.com/maven") {
                    name = "Modrinth"
                }
            }
            filter {
                includeGroup("maven.modrinth")
            }
        }
        exclusiveContent {
            forRepository {
                maven("https://maven.parchmentmc.org") {
                    name = "ParchmentMC"
                }
            }
            filter {
                includeGroupByRegex("^org.parchmentmc(\\..+)?$")
            }
        }
    }

    apply(plugin = "java")
    apply(plugin = "maven-publish")
    apply(plugin = "architectury-plugin")

    pluginManager.withPlugin("org.gradle.java-base") {
        val java: JavaPluginExtension by extensions

        java.apply {
            withSourcesJar()
            sourceCompatibility = JavaVersion.VERSION_17
            targetCompatibility = JavaVersion.VERSION_17
        }

        tasks.withType<JavaCompile>().configureEach {
            options.encoding = "UTF-8"
            options.release = 17
        }
    }

    pluginManager.withPlugin("dev.architectury.loom") {
        val loom: LoomGradleExtensionAPI by extensions

        loom.apply {
            silentMojangMappingsLicense()
        }

        dependencies {
            "minecraft"(libs.minecraft)
            "mappings"(loom.layered {
                officialMojangMappings()
                parchment(libs.parchment)
            })
        }
    }

    pluginManager.withPlugin("org.gradle.publishing") {
        val publishing: PublishingExtension by extensions

        publishing.repositories {
            val username = "MAVEN_USER".let { System.getenv(it) ?: findProperty(it) }?.toString()
            val password = "MAVEN_PASS".let { System.getenv(it) ?: findProperty(it) }?.toString()
            if (username != null && password != null) {
                maven("https://maven.aur.rocks/releases") {
                    name = "kkIncReleases"
                    mavenContent {
                        releasesOnly()
                    }
                    credentials {
                        this.username = username
                        this.password = password
                    }
                }
                maven("https://maven.aur.rocks/snapshots") {
                    name = "kkIncSnapshots"
                    mavenContent {
                        snapshotsOnly()
                    }
                    credentials {
                        this.username = username
                        this.password = password
                    }
                }
            }
        }

        publishing.publications.withType<MavenPublication>().configureEach {
            pom {
                description.convention(provider { project.description })

                licenses {
                    license {
                        name.set("GNU Lesser General Public License")
                        url.set("https://www.gnu.org/licenses/lgpl-3.0.html")
                        distribution.set("repo")
                    }
                }

                developers {
                    developer {
                        id.set("Prototik")
                        name.set("Sergey Shatunov")
                        email.set("me@aur.rocks")
                    }
                }
            }
        }
    }
}

githubRelease {
    token(findProperty("GITHUB_TOKEN")?.toString())

    val githubProject: String by rootProject
    val split = githubProject.split("/")
    owner = split[0]
    repo = split[1]
    tagName = project.version.toString()
    targetCommitish = branch
    body = changelogText
    prerelease = isBeta
    releaseAssets(
        listOf(":fabric", ":forge", ":neoforge").mapNotNull { findProject(it) }.flatMap { project ->
            listOf("remapJar", "remapSourcesJar").map { taskName ->
                { project.tasks[taskName].outputs.files }
            }
        }
    )
}

tasks.register("releaseMod") {
    group = "mod"

    dependsOn("githubRelease")
}

tasks.register("buildAll") {
    group = "mod"

    listOf(":fabric", ":forge", ":neoforge").mapNotNull { findProject(it) }.forEach { project ->
        dependsOn(project.tasks["build"])
    }
}
