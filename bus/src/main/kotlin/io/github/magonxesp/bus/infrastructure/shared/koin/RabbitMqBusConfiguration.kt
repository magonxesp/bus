package io.github.magonxesp.bus.infrastructure.shared.koin

import com.rabbitmq.client.Connection

class RabbitMqBusConfiguration : BusConfiguration() {
	var username: String = "guest"
	var password: String = "guest"
	var host: String = "localhost"
	var port: Int = 5672
	var virtualHost: String? = null
	var connection: Connection? = null
}
