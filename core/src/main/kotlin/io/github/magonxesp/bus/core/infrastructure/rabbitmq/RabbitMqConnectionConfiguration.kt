package io.github.magonxesp.bus.core.infrastructure.rabbitmq

data class RabbitMqConnectionConfiguration(
	val username: String,
	val password: String,
	val host: String = "localhost",
	val port: Int = 5672,
	val virtualHost: String? = null,
)
