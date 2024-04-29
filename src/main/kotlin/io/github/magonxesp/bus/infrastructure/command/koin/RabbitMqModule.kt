package io.github.magonxesp.bus.infrastructure.command.koin

import io.github.magonxesp.bus.domain.command.CommandBus
import io.github.magonxesp.bus.domain.command.CommandConsumer
import io.github.magonxesp.bus.domain.command.CommandHandlers
import io.github.magonxesp.bus.domain.command.CommandRegistry
import io.github.magonxesp.bus.infrastructure.command.InMemoryCommandRegistry
import io.github.magonxesp.bus.infrastructure.command.ReflectionCommandRegistry
import io.github.magonxesp.bus.infrastructure.command.rabbitmq.RabbitMqCommandBus
import io.github.magonxesp.bus.infrastructure.command.rabbitmq.RabbitMqCommandConsumer
import io.github.magonxesp.bus.infrastructure.shared.dependencyinjection.BusDependencyInjectionHelper
import io.github.magonxesp.bus.infrastructure.shared.rabbitmq.RabbitMqConfiguration
import io.github.magonxesp.bus.infrastructure.shared.rabbitmq.RabbitMqConnectionConfiguration
import io.github.magonxesp.bus.infrastructure.shared.rabbitmq.RabbitMqConnectionFactory
import org.koin.core.module.Module
import org.koin.dsl.bind
import org.koin.dsl.module

fun rabbitMqCommandBusModule(
	username: String,
	password: String,
	host: String = "localhost",
	port: Int = 5672,
	virtualHost: String? = null,
	basePackage: String,
	configuration: RabbitMqConfiguration = RabbitMqConfiguration(),
): Module {
	val connectionConfiguration = RabbitMqConnectionConfiguration(
		username = username,
		password = password,
		host = host,
		port = port,
		virtualHost = virtualHost
	)

	return module {
		single { configuration }
		single { KoinDependencyInjectionHelper() } bind BusDependencyInjectionHelper::class
		single { ReflectionCommandRegistry(basePackage) } bind CommandRegistry::class
		single { RabbitMqConnectionFactory(connectionConfiguration) }
		single { RabbitMqCommandBus(get(), get(), get()) } bind CommandBus::class
		single { RabbitMqCommandConsumer(get(), get(), get()) } bind CommandConsumer::class
	}
}

fun rabbitMqCommandBusModule(
	username: String,
	password: String,
	host: String = "localhost",
	port: Int = 5672,
	virtualHost: String? = null,
	commandHandlers: CommandHandlers,
	configuration: RabbitMqConfiguration = RabbitMqConfiguration(),
): Module {
	val connectionConfiguration = RabbitMqConnectionConfiguration(
		username = username,
		password = password,
		host = host,
		port = port,
		virtualHost = virtualHost
	)

	val registry = InMemoryCommandRegistry().apply {
		setHandlers(commandHandlers)
	}

	return module {
		single { configuration }
		single { KoinDependencyInjectionHelper() } bind BusDependencyInjectionHelper::class
		single { registry } bind CommandRegistry::class
		single { RabbitMqConnectionFactory(connectionConfiguration) }
		single { RabbitMqCommandBus(get(), get(), get()) } bind CommandBus::class
		single { RabbitMqCommandConsumer(get(), get(), get()) } bind CommandConsumer::class
	}
}
