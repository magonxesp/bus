package io.github.magonxesp.bus.infrastructure.command

import io.github.magonxesp.bus.InMemoryIntegrationTestCase
import io.github.magonxesp.bus.domain.command.UserCreateCommandHandler
import io.github.magonxesp.bus.domain.command.randomUserCreateCommand
import io.github.magonxesp.bus.infrastructure.command.inmemory.InMemorySyncCommandBus
import io.kotest.matchers.shouldBe
import org.koin.java.KoinJavaComponent.inject

class InMemorySyncCommandBusTest : InMemoryIntegrationTestCase() {
	private val bus by inject<InMemorySyncCommandBus>(InMemorySyncCommandBus::class.java)

	init {
	    test("it should dispatch and consume the command") {
			val command = randomUserCreateCommand()

			bus.dispatch(command)

			command.isConsumedBy(UserCreateCommandHandler::class) shouldBe true
		}
	}
}
