plugins {
    alias(libs.plugins.architectury.loom)
}

base {
    archivesName = rootProject.base.archivesName.map { "$it-test" }
}

architectury {
    val enabledLoaders = rootProject.properties["loaders"].toString().split(",").map { it.trim() }
    common(enabledLoaders)
}

loom {
    accessWidenerPath = project(":common").loom.accessWidenerPath
}

dependencies {
    modImplementation(libs.fabric.loader)

    implementation(project(path = ":common", configuration = "namedElements"))
}
