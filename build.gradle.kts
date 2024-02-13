plugins {
    kotlin("jvm") version "1.9.22"
    kotlin("plugin.serialization") version "1.9.22" apply false
}

group = "com.github.magonxesp"
version = "0.0.1"

repositories {
    mavenCentral()
}

subprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "org.jetbrains.kotlin.plugin.serialization")

    repositories {
        mavenCentral()
    }

    dependencies {
        val kotlin_serialization_version: String by project
        val kotlin_corroutines_version: String by project
        val kotest_version: String by project
        val moshi_version: String by project

        // implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$kotlin_serialization_version") //  remove?
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$kotlin_corroutines_version")
        implementation("com.squareup.moshi:moshi:$moshi_version")
        implementation("com.squareup.moshi:moshi-kotlin:$moshi_version")
        testImplementation(kotlin("test"))
        testImplementation("io.kotest:kotest-runner-junit5:$kotest_version")
    }

    tasks.test {
        useJUnitPlatform()
    }
    kotlin {
        jvmToolchain(21)
    }
}
