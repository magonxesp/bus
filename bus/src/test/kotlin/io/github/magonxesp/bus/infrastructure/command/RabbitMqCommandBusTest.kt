package io.github.magonxesp.bus.infrastructure.command

import io.github.magonxesp.bus.RabbitMqIntegrationTestCase
import io.github.magonxesp.bus.domain.command.UserCreateCommand
import io.github.magonxesp.bus.domain.command.randomUserCreateCommand
import io.github.magonxesp.bus.infrastructure.command.rabbitmq.RabbitMqCommandBus
import io.github.magonxesp.bus.infrastructure.command.rabbitmq.RabbitMqCommandConsumer
import io.github.magonxesp.bus.random
import org.koin.java.KoinJavaComponent.inject

class RabbitMqCommandBusTest : RabbitMqIntegrationTestCase() {
	private val commandBus by inject<RabbitMqCommandBus>(RabbitMqCommandBus::class.java)

	init {
	    test("it should dispatch a command") {
			val command = randomUserCreateCommand()

			commandBus.dispatch(command)
		}
	}
}
