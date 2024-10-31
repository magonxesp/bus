package io.github.magonxesp.bus.infrastructure.event.rabbitmq

import io.github.magonxesp.bus.domain.event.DomainEventClass
import io.github.magonxesp.bus.domain.event.DomainEventSubscriberClass

interface RabbitMqDomainEventQueueResolver {
	fun domainEventQueueName(subscriber: DomainEventSubscriberClass): String
	fun domainEventDeadLetterQueueName(subscriber: DomainEventSubscriberClass): String
	fun domainEventExchangeName(event: DomainEventClass): String
	fun domainEventDeadLetterExchangeName(event: DomainEventClass): String
	fun domainEventRoutingKey(subscriber: DomainEventSubscriberClass): String
	fun domainEventDeadLetterRoutingKey(subscriber: DomainEventSubscriberClass): String
}
