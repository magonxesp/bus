package io.github.magonxesp.example.plugins

import io.github.cdimascio.dotenv.Dotenv
import io.github.cdimascio.dotenv.dotenv
import io.ktor.server.application.*

private lateinit var dotenv: Dotenv

fun Application.configureEnvironment() {
	dotenv = dotenv {
		System.getenv("DOTENV_DIR")?.also { directory = it }
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

val rabbitMqHost: String
	get() = dotenv["RABBIT_MQ_HOST"] ?: "rabbitmq"

val rabbitMqPort: Int
	get() = dotenv["RABBIT_MQ_PORT"]?.toInt() ?: 5672

val rabbitMqUser: String
	get() = dotenv["RABBIT_MQ_USER"] ?: "guest"

val rabbitMqPassword: String
	get() = dotenv["RABBIT_MQ_PASSWORD"] ?: "guest"
