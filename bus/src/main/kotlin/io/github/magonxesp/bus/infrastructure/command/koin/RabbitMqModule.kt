package io.github.magonxesp.bus.infrastructure.command.koin

import io.github.magonxesp.bus.domain.command.CommandBus
import io.github.magonxesp.bus.domain.command.CommandConsumer
import io.github.magonxesp.bus.infrastructure.command.rabbitmq.*
import io.github.magonxesp.bus.infrastructure.shared.koin.RabbitMqBusConfiguration
import io.github.magonxesp.bus.infrastructure.shared.koin.rabbitMqConnectionFactory
import org.koin.core.module.Module
import org.koin.dsl.bind
import org.koin.dsl.module

fun rabbitMqCommandBusModule(configure: RabbitMqBusConfiguration.() -> Unit): Module = module {
	val configuration = RabbitMqBusConfiguration().apply { configure() }

	commonDependencies()
	commandRegistryModule(configuration)
	rabbitMqConnectionFactory(configuration)

	single { RabbitMqCommandQueueAutoDeclaration(get(), get(), get()) }
	single { DefaultRabbitMqCommandQueueResolver(get()) } bind RabbitMqCommandQueueResolver::class
	single { RabbitMqCommandBus(get(), get(), get(), get()) } bind CommandBus::class
	single { RabbitMqCommandConsumer(get(), get(), get(), get(), get()) } bind CommandConsumer::class
}
