package io.github.magonxesp.example.plugins

import io.github.cdimascio.dotenv.Dotenv
import io.github.cdimascio.dotenv.dotenv
import io.ktor.server.application.*

private lateinit var dotenv: Dotenv

fun Application.configureEnvironment() {
	dotenv = dotenv {
		System.getProperty("dotenv.dir")?.also { directory = it }
		ignoreIfMissing = true
		systemProperties = true
	}
}

val postgreSqlUrl: String
	get() = dotenv["POSTGRESQL_URL"] ?: "jdbc:postgresql://postgresql:5432/example"

val postgreSqlUser: String
	get() = dotenv["POSTGRESQL_USER"] ?: "example"

val postgreSqlPassword: String
	get() = dotenv["POSTGRESQL_PASSWORD"] ?: "example"
