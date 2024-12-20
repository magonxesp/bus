plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
	id("io.ktor.plugin") version "3.0.0-rc-1"
}

group = "com.example"
version = "0.0.1"

application {
    mainClass.set("io.github.magonxesp.example.rabbitmq.ApplicationKt")
}

dependencies {
	val kotlin_version: String by project
	val logback_version: String by project
	val postgres_version: String by project
	val exposed_version: String by project
	val influxdb_version: String by project
	val koin_version: String by project

	implementation("io.ktor:ktor-server-core-jvm")
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm")
    implementation("io.ktor:ktor-server-content-negotiation-jvm")
    implementation("org.postgresql:postgresql:$postgres_version")
	implementation("org.jetbrains.exposed:exposed-core:$exposed_version")
	implementation("org.jetbrains.exposed:exposed-jdbc:$exposed_version")
    implementation("io.ktor:ktor-server-metrics-jvm")
    implementation("io.ktor:ktor-server-metrics-micrometer-jvm")
    implementation("io.micrometer:micrometer-registry-influx:$influxdb_version")
    implementation("io.ktor:ktor-server-call-logging-jvm")
    implementation("io.ktor:ktor-server-netty-jvm")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    implementation("io.ktor:ktor-server-config-yaml")
	implementation("io.insert-koin:koin-core:$koin_version")
	implementation(project(":examples:books-marketplace"))
	implementation(project(":bus"))
    testImplementation("io.ktor:ktor-server-test-host-jvm")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
}
