package io.github.magonxesp.bus.infrastructure.command

import io.github.magonxesp.bus.RabbitMqIntegrationTestCase
import io.github.magonxesp.bus.domain.command.UserCreateCommand
import io.github.magonxesp.bus.domain.command.UserCreateCommandHandler
import io.github.magonxesp.bus.domain.command.randomUserCreateCommand
import io.github.magonxesp.bus.infrastructure.command.rabbitmq.RabbitMqCommandBus
import io.github.magonxesp.bus.infrastructure.command.rabbitmq.RabbitMqCommandConsumer
import io.github.magonxesp.bus.infrastructure.shared.dependencyinjection.TestDependencyInjectionContainer
import io.github.magonxesp.bus.random
import io.kotest.core.spec.Spec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.delay

class RabbitMqCommandConsumerTest : RabbitMqIntegrationTestCase() {
	private lateinit var commandBus: RabbitMqCommandBus
	private lateinit var commandConsumer: RabbitMqCommandConsumer

	override suspend fun beforeSpec(spec: Spec) {
		super.beforeSpec(spec)
		commandBus = RabbitMqCommandBus(connectionFactory, commandRegistry)
		commandConsumer = RabbitMqCommandConsumer(commandRegistry, TestDependencyInjectionContainer(), connectionFactory)
	}

	init {
	    test("it should consume commands") {
			val command = randomUserCreateCommand()

			commandBus.dispatch(command)
			commandConsumer.startConsume()

			delay(3000)
			command.isConsumedBy(UserCreateCommandHandler::class) shouldBe true
		}
	}
}
