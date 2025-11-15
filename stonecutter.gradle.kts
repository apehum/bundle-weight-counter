plugins {
    id("dev.kikugie.stonecutter")
}
stonecutter active "1.21.2-fabric"

stonecutter parameters {
    constants.match(node.metadata.project.substringAfterLast('-'), "fabric", "neoforge")
}

allprojects {
    repositories {
        mavenCentral()
        mavenLocal()
        maven("https://maven.neoforged.net/releases")
        maven("https://maven.fabricmc.net/")
        maven("https://maven.shedaniel.me/")
        maven("https://maven.terraformersmc.com/releases/")
    }
}
