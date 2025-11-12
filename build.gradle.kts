plugins {
    id("fabric-loom") version "1.11-SNAPSHOT"
}

version = "${property("mod_version")}+${stonecutter.current.version}"
group = property("maven_group")!!

base {
    archivesName = property("archives_base_name") as String
}

repositories {
    maven("https://maven.shedaniel.me/")
    maven("https://maven.terraformersmc.com/releases/")
}

dependencies {
    minecraft("com.mojang:minecraft:${property("minecraft_version")}")
    mappings(loom.officialMojangMappings())
    modImplementation("net.fabricmc:fabric-loader:${property("loader_version")}")
    modApi("me.shedaniel.cloth:cloth-config-fabric:${property("cloth_version")}") {
        exclude("net.fabricmc.fabric-api")
    }
    modCompileOnly("com.terraformersmc:modmenu:${property("modmenu_version")}")
    modRuntimeOnly("net.fabricmc.fabric-api:fabric-api:${property("fabric_version")}")
}

tasks {
    processResources {
        filteringCharset = "UTF-8"

        filesMatching("fabric.mod.json") {
            expand(
                mapOf(
                    "version" to project.version,
                    "minecraft_version_dependency" to project.property("minecraft_version_dependency"),
                    "loader_version" to project.property("loader_version"),
                )
            )
        }
    }

    withType<JavaCompile>().configureEach {
        options.encoding = "UTF-8"
        options.release.set(21)
    }

    jar {
        from("LICENSE") {
            rename { "${it}_${project.property("archives_base_name")}" }
        }
    }

    val copyToRoot = register<Copy>("copyToRoot") {
        dependsOn(remapJar)
        from(remapJar.map { it.archiveFile.get() })
        into(rootProject.layout.buildDirectory.dir("libs"))
    }

    build {
        dependsOn(copyToRoot)
        doLast {
            remapJar.get().archiveFile.get()
        }
    }
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}

loom {
    runConfigs.all {
        ideConfigGenerated(true)
        runDir = "../../run"
    }
}
