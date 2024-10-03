package io.github.magonxesp.bus.infrastructure.command.rabbitmq

import io.github.magonxesp.bus.domain.command.Command
import io.github.magonxesp.bus.domain.command.CommandBus
import io.github.magonxesp.bus.domain.command.CommandRegistry
import io.github.magonxesp.bus.infrastructure.command.serializeToJson
import io.github.magonxesp.bus.infrastructure.shared.rabbitmq.RabbitMqConfiguration
import io.github.magonxesp.bus.infrastructure.shared.rabbitmq.RabbitMqConnectionFactory

class RabbitMqCommandBus(
	private val connectionFactory: RabbitMqConnectionFactory,
	private val commandRegistry: CommandRegistry,
	configuration: RabbitMqConfiguration = RabbitMqConfiguration()
) : RabbitMqCommandClient(configuration), CommandBus {
	override fun dispatch(command: Command) {
		val commandHandlers = commandRegistry.commandHandlers()
		val connection = connectionFactory.getConnection()
		val channel = connection.createChannel()
		val commandHandler = commandHandlers[command::class]
			?: error("Unable to publish command ${command::class}. Command handler for ${command::class} not found")

		channel.basicPublish(exchangeName, commandHandler.routingKey, null, command.serializeToJson().toByteArray())
	}
}
