package io.github.magonxesp.bus.infrastructure.shared.koin

import com.rabbitmq.client.Connection
import com.rabbitmq.client.ConnectionFactory
import org.koin.core.module.Module
import org.koin.dsl.bind
import kotlin.math.sin

fun Module.rabbitMqConnectionFactory(configuration: RabbitMqBusConfiguration) {
	if (configuration.connection != null) {
		single { configuration.connection!! } bind Connection::class
		return
	}

	single {
		ConnectionFactory().apply {
			username = configuration.username
			password = configuration.password
			host = configuration.host
			port = configuration.port
			configuration.virtualHost?.also { virtualHost = it }
		}.newConnection()
	} bind Connection::class
}
