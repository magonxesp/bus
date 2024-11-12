package io.github.magonxesp.bus.infrastructure.command.rabbitmq

import com.rabbitmq.client.Connection
import io.github.magonxesp.bus.domain.command.Command
import io.github.magonxesp.bus.domain.command.CommandBus
import io.github.magonxesp.bus.domain.command.CommandRegistry
import io.github.magonxesp.bus.infrastructure.command.CommandSerializer
import io.github.magonxesp.bus.infrastructure.shared.withTimeSpentLog
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class RabbitMqCommandBus(
	private val connection: Connection,
	private val commandRegistry: CommandRegistry,
	private val commandSerializer: CommandSerializer,
	private val commandQueueResolver: RabbitMqCommandQueueResolver
) : CommandBus {
	override fun dispatch(command: Command<*>): Unit = withTimeSpentLog(::dispatch) {
		val commandHandlers = commandRegistry.commandHandlers()
		val channel = connection.createChannel()
		val commandHandler = commandHandlers[command::class]
			?: error("Unable to publish command ${command::class}. Command handler for ${command::class} not found")

		val exchange = commandQueueResolver.commandExchangeName(commandHandler)
		val routingKey = commandQueueResolver.commandRoutingKey(commandHandler)
		val serializedCommand = commandSerializer.serialize(command)

		channel.basicPublish(exchange, routingKey, null, serializedCommand.toByteArray())
	}
}
