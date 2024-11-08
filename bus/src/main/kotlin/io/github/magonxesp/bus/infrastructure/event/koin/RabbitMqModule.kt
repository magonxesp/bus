package io.github.magonxesp.bus.infrastructure.event.koin

import io.github.magonxesp.bus.domain.event.DomainEventBus
import io.github.magonxesp.bus.domain.event.DomainEventConsumer
import io.github.magonxesp.bus.infrastructure.event.rabbitmq.*
import io.github.magonxesp.bus.infrastructure.shared.koin.RabbitMqBusConfiguration
import io.github.magonxesp.bus.infrastructure.shared.koin.rabbitMqConnectionFactory
import org.koin.core.module.Module
import org.koin.dsl.bind
import org.koin.dsl.module

fun rabbitMqDomainEventBusModule(configure: RabbitMqBusConfiguration.() -> Unit): Module = module {
	val configuration = RabbitMqBusConfiguration().apply { configure() }

	commonDomainEventDependencies()
	domainEventRegistryModule(configuration)
	rabbitMqConnectionFactory(configuration)

	single { RabbitMqDomainEventQueueAutoDeclaration(get(), get(), get()) }
	single { DefaultRabbitMqDomainEventQueueResolver(configuration.queuePrefix) } bind RabbitMqDomainEventQueueResolver::class
	single { RabbitMqDomainEventBus(get(), get(), get()) } bind DomainEventBus::class

	if (configuration.consumePolling) {
		single { RabbitMqPollingDomainEventConsumer(get(), get(), get(), get(), get()) } bind DomainEventConsumer::class
	} else {
		single { RabbitMqDomainEventConsumer(get(), get(), get(), get(), get()) } bind DomainEventConsumer::class
	}
}
