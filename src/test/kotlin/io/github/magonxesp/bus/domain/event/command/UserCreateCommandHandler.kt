package io.github.magonxesp.bus.domain.event.command

import io.github.magonxesp.bus.IntegrationTestCase
import io.github.magonxesp.bus.domain.command.CommandHandler
import org.slf4j.LoggerFactory
import kotlin.io.path.Path
import kotlin.io.path.writeText

class UserCreateCommandHandler : CommandHandler<UserCreateCommand> {
	private val logger = LoggerFactory.getLogger(this::class.java)

	override fun handle(command: UserCreateCommand) {
		logger.debug("Command handler {} fired", this::class)
		val directory = Path(IntegrationTestCase.TEST_TMP_DIR, "command").toFile().apply { mkdirs() }
		Path(directory.path, this::class.qualifiedName!!).writeText("")
		logger.debug("Command handler {} finish", this::class)
	}
}
