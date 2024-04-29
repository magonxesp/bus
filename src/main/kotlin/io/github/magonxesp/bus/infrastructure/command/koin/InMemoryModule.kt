package io.github.magonxesp.bus.infrastructure.command.koin

import io.github.magonxesp.bus.domain.command.CommandBus
import io.github.magonxesp.bus.domain.command.CommandHandlers
import io.github.magonxesp.bus.domain.command.CommandRegistry
import io.github.magonxesp.bus.infrastructure.command.InMemoryCommandRegistry
import io.github.magonxesp.bus.infrastructure.command.ReflectionCommandRegistry
import io.github.magonxesp.bus.infrastructure.command.inmemory.InMemorySyncCommandBus
import io.github.magonxesp.bus.infrastructure.shared.dependencyinjection.BusDependencyInjectionHelper
import io.github.magonxesp.bus.infrastructure.shared.rabbitmq.RabbitMqConfiguration
import org.koin.core.module.Module
import org.koin.dsl.bind
import org.koin.dsl.module

fun inMemoryCommandBusModule(
	basePackage: String,
	configuration: RabbitMqConfiguration = RabbitMqConfiguration(),
): Module {
	return module {
		single { configuration }
		single { KoinDependencyInjectionHelper() } bind BusDependencyInjectionHelper::class
		single { ReflectionCommandRegistry(basePackage) } bind CommandRegistry::class
		single { InMemorySyncCommandBus(get(), get()) } bind CommandBus::class
	}
}

fun inMemoryCommandBusModule(
	commandHandlers: CommandHandlers,
	configuration: RabbitMqConfiguration = RabbitMqConfiguration(),
): Module {
	val registry = InMemoryCommandRegistry().apply {
		setHandlers(commandHandlers)
	}

	return module {
		single { configuration }
		single { KoinDependencyInjectionHelper() } bind BusDependencyInjectionHelper::class
		single { registry } bind CommandRegistry::class
		single { InMemorySyncCommandBus(get(), get()) } bind CommandBus::class
	}
}
