package io.github.magonxesp.bus.infrastructure.command

import io.github.magonxesp.bus.RabbitMqIntegrationTestCase
import io.github.magonxesp.bus.domain.command.UserCreateCommand
import io.github.magonxesp.bus.domain.command.randomUserCreateCommand
import io.github.magonxesp.bus.infrastructure.command.rabbitmq.RabbitMqCommandBus
import io.github.magonxesp.bus.random

class RabbitMqCommandBusTest : RabbitMqIntegrationTestCase() {
	init {
	    test("it should dispatch a command") {
			val bus = RabbitMqCommandBus(connectionFactory, commandRegistry)
			val command = randomUserCreateCommand()

			bus.dispatch(command)
		}
	}
}
