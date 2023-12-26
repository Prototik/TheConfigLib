<center><div align="center">

# TheConfigLib

![Enviroment](https://img.shields.io/badge/Enviroment-Client-purple)
[![Java 17](https://img.shields.io/badge/Language-Java%2017-9B599A.svg?color=orange)](https://www.oracle.com/news/announcement/oracle-releases-java-17-2021-09-14)

[![Latest Snapshot](https://maven.aur.rocks/api/badge/latest/snapshots/dev/tcl/the-config-lib-common?name=Snapshot)](https://maven.aur.rocks/#/snapshots/dev/tcl)

[//]: # ([![Modrinth]&#40;https://img.shields.io/modrinth/dt/1eAoo2KR?color=00AF5C&label=downloads&logo=modrinth&#41;]&#40;https://modrinth.com/mod/yacl&#41;)
[//]: # ([![CurseForge]&#40;https://img.shields.io/curseforge/dt/667299?logo=curseforge&color=E04E14&#41;]&#40;https://curseforge.com/minecraft/mc-mods/yacl&#41;)

The Config Lib, one and only config library for your mods!

</div></center>

## Development environment setup

Below described artifacts provide in this maven repository:

### Gradle Groovy
```groovy
maven {
    name "kkIncReleases"
    url "https://maven.aur.rocks/releases"
    mavenContent {
        releasesOnly()
    }
}
maven {
    name "kkIncSnapshots"
    url "https://maven.aur.rocks/snapshots"
    mavenContent {
        snapshotsOnly()
    }
}
```

### Gradle Kotlin
```kotlin
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
```

### Architectury Loom / Fabric Loom

#### Common API
```kotlin
modCompileOnlyApi("dev.tcl:the-config-lib-common:$tcl_version")
```

#### Fabric
```kotlin
modImplementation("dev.tcl:the-config-lib-fabric:$tcl_version")
```

#### Forge
```kotlin
modImplementation("dev.tcl:the-config-lib-forge:$tcl_version")
```

#### NeoForge
```kotlin
modImplementation("dev.tcl:the-config-lib-neoforge:$tcl_version")
```

## License

This mod is under the [GNU Lesser General Public License, v3.0](LICENSE).
