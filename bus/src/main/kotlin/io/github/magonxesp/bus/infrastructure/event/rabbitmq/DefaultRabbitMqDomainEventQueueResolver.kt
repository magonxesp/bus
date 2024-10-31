package io.github.magonxesp.bus.infrastructure.event.rabbitmq

import io.github.magonxesp.bus.domain.command.CommandHandler
import io.github.magonxesp.bus.domain.command.CommandHandlerClass
import io.github.magonxesp.bus.domain.event.DomainEventClass
import io.github.magonxesp.bus.domain.event.DomainEventSubscriberClass
import io.github.magonxesp.bus.domain.shared.camelToSnakeCase
import kotlin.reflect.KClass

class DefaultRabbitMqDomainEventQueueResolver(private val queuePrefix: String) : RabbitMqDomainEventQueueResolver {
	override fun domainEventQueueName(subscriber: DomainEventSubscriberClass): String =
		queuePrefix + (subscriber.simpleName?.camelToSnakeCase() ?: error("The command handler must be defined and not be an anonymous definition"))

	override fun domainEventDeadLetterQueueName(subscriber: DomainEventSubscriberClass): String = domainEventQueueName(subscriber) + ".dlx"

	override fun domainEventExchangeName(event: DomainEventClass): String = "domain_event.${event.simpleName}"

	override fun domainEventDeadLetterExchangeName(event: DomainEventClass): String = domainEventExchangeName(event) + ".dlx"

	override fun domainEventRoutingKey(subscriber: DomainEventSubscriberClass): String = "domain_event_subscriber." + domainEventQueueName(subscriber)

	override fun domainEventDeadLetterRoutingKey(subscriber: DomainEventSubscriberClass): String = domainEventRoutingKey(subscriber) + ".dlx"
}
