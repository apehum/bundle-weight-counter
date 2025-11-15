pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://maven.fabricmc.net/") { name = "Fabric" }
    }
}

plugins {
    id("dev.kikugie.stonecutter") version "0.7.10"
}

stonecutter {
    create(rootProject) {
        fun mc(mcVersion: String, vararg loaders: String) =
            loaders.forEach { version("$mcVersion-$it", mcVersion) }

        mc("1.21.2", "fabric", "neoforge")
        mc("1.21.5", "fabric", "neoforge")
    }
}

rootProject.name = "bundle-weight-counter"
