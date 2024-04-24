package io.github.magonxesp.bus.infrastructure.command

import io.github.magonxesp.bus.RabbitMqIntegrationTestCase
import io.github.magonxesp.bus.domain.event.command.UserCreateCommand
import io.github.magonxesp.bus.infrastructure.command.rabbitmq.RabbitMqCommandBus
import io.github.magonxesp.bus.random

class RabbitMqCommandBusTest : RabbitMqIntegrationTestCase() {
	init {
	    test("it should dispatch a command") {
			val bus = RabbitMqCommandBus(connectionFactory, commandRegistry)
			val command = UserCreateCommand(
				username = random().random.randomString(30),
				email = random().internet.email(),
				roles = setOf("GUEST", "VISITOR")
			)

			bus.dispatch(command)
		}
	}
}
