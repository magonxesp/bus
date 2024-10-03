package io.github.magonxesp.bus.domain.command

import io.github.magonxesp.bus.IntegrationTestCase.Companion.notifyConsumed
import org.slf4j.LoggerFactory

class UserCreateCommandHandler : CommandHandler<UserCreateCommand> {
	private val logger = LoggerFactory.getLogger(this::class.java)

	override fun handle(command: UserCreateCommand) {
		logger.debug("Command handler {} fired", this::class)
		notifyConsumed(command)
		logger.debug("Command handler {} finish", this::class)
	}
}
