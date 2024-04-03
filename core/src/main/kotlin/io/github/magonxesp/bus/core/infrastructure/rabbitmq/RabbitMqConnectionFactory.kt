package io.github.magonxesp.bus.core.infrastructure.rabbitmq

import com.rabbitmq.client.Connection
import com.rabbitmq.client.ConnectionFactory
import kotlin.concurrent.thread

class RabbitMqConnectionFactory(private val configuration: RabbitMqConnectionConfiguration) {
	companion object {
		private var connection: Connection? = null
	}

	private fun closeConnectionOnShutdown() {
		Runtime.getRuntime().addShutdownHook(thread(start = false) {
			if (connection != null) {
				connection!!.close()
			}
		})
	}

	private fun createNewConnection(): Connection {
		val factory = ConnectionFactory()

		factory.username = configuration.username
		factory.password = configuration.password
		factory.host = configuration.host
		factory.port = configuration.port
		configuration.virtualHost?.also { factory.virtualHost = it }

		return factory.newConnection()
	}

	fun getConnection(): Connection {
		if (connection == null) {
			connection = createNewConnection()
			closeConnectionOnShutdown()
		}

		return connection!!
	}
}
