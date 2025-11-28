import com.github.apehum.bundlecounter.VersionResolver

plugins {
    id("dev.isxander.modstitch.base") version "0.7.0-unstable"
    id("me.modmuss50.mod-publish-plugin") version "1.1.0"
}

val platform = stonecutter.current.project.substringAfter('-')

base {
    archivesName = "${property("archives_base_name")}-$platform"
}

group = property("maven_group")!!

val minecraft = property("minecraft_version") as String

modstitch {
    minecraftVersion = minecraft

    loom {
        fabricLoaderVersion = property("deps.fabric_loader_version") as String

        configureLoom {
            runs.all {
                runDir = "../../run"
            }
        }
    }

    moddevgradle {
        neoForgeVersion = property("deps.neoforge") as? String

        defaultRuns(true, false)

        configureNeoForge {
            runs.all {
                gameDirectory = file("../../run")
            }
        }
    }

    metadata {
        modId = "bundleweightcounter"
        modVersion = "${property("mod_version")}+${stonecutter.current.version}"
        modName = "Bundle Weight Counter"
        modDescription = "Shows the weight of items in the bundle"

        replacementProperties.apply {
            put("fabric_loader_version", property("deps.fabric_loader_version") as String)
            put("minecraft_version_dependency", property("minecraft_version_dependency") as String)
        }
    }

    mixin {
        addMixinsToModManifest = true

        configs.register("bundlecounter")
    }
}

dependencies {
    modstitch.loom {
        modstitchModImplementation("net.fabricmc.fabric-api:fabric-api:${property("deps.fabric_api")}")

        modstitchModImplementation("me.shedaniel.cloth:cloth-config-fabric:${property("deps.cloth_config")}") {
            exclude("net.fabricmc.fabric-api")
        }
        modstitchModImplementation("com.terraformersmc:modmenu:${property("deps.modmenu")}")
    }

    modstitch.moddevgradle {
        modstitchModImplementation("me.shedaniel.cloth:cloth-config-neoforge:${property("deps.cloth_config")}")
    }
}

val outputJarTask = modstitch.finalJarTask

tasks {
    jar {
        from("LICENSE") {
            rename { "${it}_${project.property("archives_base_name")}" }
        }
    }

    val copyToRoot =
        register<Copy>("copyToRoot") {
            dependsOn(outputJarTask)
            from(outputJarTask.map { it.archiveFile.get() })
            into(rootProject.layout.buildDirectory.dir("libs"))
        }

    build {
        dependsOn(copyToRoot)
    }
}

publishMods {
    changelog =
        rootProject.layout.projectDirectory
            .file("changelog.md")
            .asFile
            .readText()
    type = STABLE
    modLoaders.add(platform)

    val loaderDisplayName =
        when (platform) {
            "fabric" -> "Fabric"
            "neoforge" -> "NeoForge"
            else -> throw IllegalStateException("Unsupported platform $platform")
        }

    displayName = "[$loaderDisplayName ${property("minecraft_version")}] Bundle Weight Counter ${property("mod_version")}"
    file = outputJarTask.get().archiveFile
    dryRun =
        providers.environmentVariable("MODRINTH_TOKEN").getOrNull()?.takeIf { it.isNotBlank() } == null ||
        providers.environmentVariable("CURSEFORGE_TOKEN").getOrNull()?.takeIf { it.isNotBlank() } == null

    val versions =
        VersionResolver
            .getMinecraftVersionsInRange("release", property("minecraft_version_dependency") as String)
            .get()
            .map { it.id }

    modrinth {
        projectId = "ExWOxDxj"
        accessToken = providers.environmentVariable("MODRINTH_TOKEN")
        minecraftVersions.addAll(versions)
    }

    curseforge {
        projectId = "1132365"
        accessToken = providers.environmentVariable("CURSEFORGE_TOKEN")
        minecraftVersions.addAll(versions)
    }
}
