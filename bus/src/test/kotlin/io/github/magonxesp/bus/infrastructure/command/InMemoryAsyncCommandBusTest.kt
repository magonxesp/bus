package io.github.magonxesp.bus.infrastructure.command

import io.github.magonxesp.bus.InMemoryIntegrationTestCase
import io.github.magonxesp.bus.domain.command.UserCreateCommandHandler
import io.github.magonxesp.bus.domain.command.randomUserCreateCommand
import io.github.magonxesp.bus.infrastructure.command.inmemory.InMemoryAsyncCommandBus
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.delay
import org.koin.java.KoinJavaComponent.inject

class InMemoryAsyncCommandBusTest : InMemoryIntegrationTestCase() {
	private val bus by inject<InMemoryAsyncCommandBus>(InMemoryAsyncCommandBus::class.java)

	init {
	    test("it should dispatch and consume the command") {
			val command = randomUserCreateCommand()

			bus.dispatch(command)
			delay(1000)

			command.isConsumedBy(UserCreateCommandHandler::class) shouldBe true
		}
	}
}
