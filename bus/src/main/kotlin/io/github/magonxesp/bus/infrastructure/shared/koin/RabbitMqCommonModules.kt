package io.github.magonxesp.bus.infrastructure.shared.koin

import com.rabbitmq.client.ConnectionFactory
import org.koin.core.module.Module

fun Module.rabbitMqConnectionFactory(configuration: RabbitMqBusConfiguration) {
	single {
		when {
			configuration.connection != null -> configuration.connection
			else -> ConnectionFactory().apply {
				username = configuration.username
				password = configuration.password
				host = configuration.host
				port = configuration.port
				configuration.virtualHost?.also { virtualHost = it }
			}.newConnection()
		}
	}
}
