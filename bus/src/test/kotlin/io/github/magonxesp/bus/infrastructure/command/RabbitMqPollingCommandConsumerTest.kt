package io.github.magonxesp.bus.infrastructure.command

import io.github.magonxesp.bus.RabbitMqIntegrationTestCase
import io.github.magonxesp.bus.domain.command.UserCreateCommandHandler
import io.github.magonxesp.bus.domain.command.randomUserCreateCommand
import io.github.magonxesp.bus.infrastructure.command.rabbitmq.RabbitMqCommandBus
import io.github.magonxesp.bus.infrastructure.command.rabbitmq.RabbitMqCommandConsumer
import io.github.magonxesp.bus.infrastructure.command.rabbitmq.RabbitMqPollingCommandConsumer
import io.github.magonxesp.bus.shouldBeConsumedBy
import kotlinx.coroutines.delay
import org.koin.java.KoinJavaComponent.inject

class RabbitMqPollingCommandConsumerTest : RabbitMqIntegrationTestCase() {
	override val injectPollingConsumer = true
	private val commandBus by inject<RabbitMqCommandBus>(RabbitMqCommandBus::class.java)
	private val commandConsumer by inject<RabbitMqPollingCommandConsumer>(RabbitMqPollingCommandConsumer::class.java)

	init {
	    test("it should consume commands") {
			val command = randomUserCreateCommand()

			commandBus.dispatch(command)
			commandConsumer.startConsume()

			delay(3000)
			command shouldBeConsumedBy UserCreateCommandHandler::class
		}
	}
}
