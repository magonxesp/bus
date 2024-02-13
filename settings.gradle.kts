pluginManagement {
    repositories {
        mavenLocal()
        gradlePluginPortal()
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}

rootProject.name = "bus"

include(":command")
include(":event")
include(":query")
include(":shared")
include(":plugin")
include(":examples:in-memory-command-bus")
