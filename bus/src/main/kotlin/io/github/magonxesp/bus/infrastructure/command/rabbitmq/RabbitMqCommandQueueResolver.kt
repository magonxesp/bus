package io.github.magonxesp.bus.infrastructure.command.rabbitmq

import io.github.magonxesp.bus.domain.command.CommandHandlerClass

interface RabbitMqCommandQueueResolver {
	fun commandQueueName(handler: CommandHandlerClass): String
	fun commandDeadLetterQueueName(handler: CommandHandlerClass): String
	fun commandExchangeName(handler: CommandHandlerClass): String
	fun commandDeadLetterExchangeName(handler: CommandHandlerClass): String
	fun commandRoutingKey(handler: CommandHandlerClass): String
	fun commandDeadLetterRoutingKey(handler: CommandHandlerClass): String
}
