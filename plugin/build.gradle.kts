plugins {
	`java-gradle-plugin`
	id("com.gradle.plugin-publish") version "1.2.1"
}

group = "io.github.magonxesp"
version = "0.0.1"

gradlePlugin {
	plugins {
		create("busPlugin") {
			id = "io.github.magonxesp.bus"
			implementationClass = "com.magonxesp.bus.plugin.BusPlugin"
			website = "https://github.com/magonxesp"
			vcsUrl = "https://github.com/magonxesp/bus"
			displayName = "Bus Registry"
			description = """
				Command/Query/Event Bus support plugin. Scans classes that are serializable for publish to a command/query/event bus and
				register them (on a json file) for call the Listeners/Handlers of the Bus on incoming messages.
			""".trimIndent()
			tags = setOf("command bus", "event bus", "query bus", "bus", "support plugin")
		}
	}
}

publishing {
	repositories {
		mavenLocal()
	}
}

dependencies {
	val kotlin_version: String by rootProject

	implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version")
}
