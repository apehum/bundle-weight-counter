pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://maven.fabricmc.net/") { name = "Fabric" }
    }
}

plugins {
    id("dev.kikugie.stonecutter") version "0.7.1"
}

stonecutter {
    create(rootProject) {
        versions("1.21.2", "1.21.5", "1.21.9", "1.21.10")
    }
}

rootProject.name = "bundle-weight-counter"