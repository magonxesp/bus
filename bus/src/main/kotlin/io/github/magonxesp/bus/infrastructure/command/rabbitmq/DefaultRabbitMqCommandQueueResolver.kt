package io.github.magonxesp.bus.infrastructure.command.rabbitmq

import io.github.magonxesp.bus.domain.command.CommandHandler
import io.github.magonxesp.bus.domain.command.CommandHandlerClass
import io.github.magonxesp.bus.domain.shared.camelToSnakeCase
import kotlin.reflect.KClass

class DefaultRabbitMqCommandQueueResolver(private val queuePrefix: String) : RabbitMqCommandQueueResolver {
	override fun commandQueueName(handler: CommandHandlerClass): String =
		queuePrefix + (handler.simpleName?.camelToSnakeCase() ?: error("The command handler must be defined and not be an anonymous definition"))

	override fun commandDeadLetterQueueName(handler: CommandHandlerClass): String =
		commandQueueName(handler) + ".dlx"

	override fun commandExchangeName(handler: CommandHandlerClass): String = "command"

	override fun commandDeadLetterExchangeName(handler: CommandHandlerClass): String =
		commandExchangeName(handler) + ".dlx"

	override fun commandRoutingKey(handler: CommandHandlerClass): String =
		"${commandExchangeName(handler)}.${commandQueueName(handler)}"

	override fun commandDeadLetterRoutingKey(handler: CommandHandlerClass): String =
		"${commandDeadLetterExchangeName(handler)}.${commandDeadLetterQueueName(handler)}"
}
