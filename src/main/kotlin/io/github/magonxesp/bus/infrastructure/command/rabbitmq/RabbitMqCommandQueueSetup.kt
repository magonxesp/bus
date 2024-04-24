package io.github.magonxesp.bus.infrastructure.command.rabbitmq

import io.github.magonxesp.bus.domain.command.CommandRegistry
import io.github.magonxesp.bus.infrastructure.shared.rabbitmq.RabbitMqConfiguration
import io.github.magonxesp.bus.infrastructure.shared.rabbitmq.RabbitMqConnectionFactory

class RabbitMqCommandQueueSetup(
	private val connectionFactory: RabbitMqConnectionFactory,
	private val commandRegistry: CommandRegistry,
	configuration: RabbitMqConfiguration = RabbitMqConfiguration()
) : RabbitMqCommandClient(configuration) {
	fun setupQueues() {
		val connection = connectionFactory.getConnection()
		val channel = connection.createChannel()
		val commandHandlers = commandRegistry.commandHandlers()

		for ((_, handlerClass) in commandHandlers.entries) {
			channel.createQueue(handlerClass)
		}
	}
}
