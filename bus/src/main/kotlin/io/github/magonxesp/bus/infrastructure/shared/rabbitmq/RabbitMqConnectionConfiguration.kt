package io.github.magonxesp.bus.infrastructure.shared.rabbitmq

data class RabbitMqConnectionConfiguration(
	val username: String,
	val password: String,
	val host: String = "localhost",
	val port: Int = 5672,
	val virtualHost: String? = null,
)
