package io.github.magonxesp.bus.infrastructure.shared.rabbitmq

import com.rabbitmq.client.Channel
import com.rabbitmq.client.Connection
import com.rabbitmq.client.ConnectionFactory
import org.slf4j.LoggerFactory
import kotlin.concurrent.thread

class RabbitMqConnectionFactory(private val configuration: RabbitMqConnectionConfiguration) {
	private val logger = LoggerFactory.getLogger(this::class.java)

	companion object {
		private var connection: Connection? = null
		private val shutdownTasks = mutableListOf<() -> Unit>()
	}

	private fun closeConnectionOnShutdown() {
		Runtime.getRuntime().addShutdownHook(thread(start = false) {
			if (connection != null && connection!!.isOpen) {
				logger.info("Closing RabbitMQ connection...")
				for (shutDownTask in shutdownTasks) {
					shutDownTask()
				}

				connection!!.close()
				logger.info("RabbitMQ connection closed!")
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

		// if the connection is closed, and we want a connection create a new one
		if (connection != null && !connection!!.isOpen) {
			connection = createNewConnection()
		}

		return connection!!
	}

	/**
	 * Add a shutdown task when closing the connection gracefully
	 */
	fun addShutdownTask(task: () -> Unit) {
		shutdownTasks.add(task)
	}
}
