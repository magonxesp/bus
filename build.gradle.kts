import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    kotlin("jvm") version "1.9.22"
    kotlin("plugin.serialization") version "1.9.22"
}

group = "io.github.magonxesp"
version = "0.0.1"

repositories {
    mavenCentral()
}

java {
	targetCompatibility = JavaVersion.VERSION_1_8
	sourceCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
	val kotlin_serialization_version: String by project
	val kotlin_corroutines_version: String by project
	val kotlin_datetime_version: String by project
	val kotest_version: String by project
	val moshi_version: String by project
	val rabbitmq_client_version: String by project
	val reflections_version: String by project

	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$kotlin_corroutines_version")
	implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$kotlin_serialization_version")
	implementation("org.jetbrains.kotlinx:kotlinx-datetime:$kotlin_datetime_version")
	implementation("com.squareup.moshi:moshi:$moshi_version")
	implementation("com.squareup.moshi:moshi-kotlin:$moshi_version")
	implementation("com.rabbitmq:amqp-client:$rabbitmq_client_version")
	implementation("org.reflections:reflections:$reflections_version")
	testImplementation(kotlin("test"))
	testImplementation("io.kotest:kotest-runner-junit5:$kotest_version")
}

tasks.test {
	useJUnitPlatform()
}

kotlin {
	compilerOptions {
		jvmTarget.set(JvmTarget.JVM_1_8)
	}
}
