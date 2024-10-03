package io.github.magonxesp.example.service

import io.github.magonxesp.bus.domain.command.CommandHandler
import io.github.magonxesp.example.model.UserCommand
import io.github.magonxesp.example.repository.UserRepository
import org.slf4j.LoggerFactory

class UserSaveCommandHandler(private val repository: UserRepository) : CommandHandler<UserCommand> {
	private val logger = LoggerFactory.getLogger(this::class.java)

	override fun handle(command: UserCommand) {
		repository.save(command.toUser())
		logger.info("User saved")
	}
}
