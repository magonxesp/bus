package io.github.magonxesp.example.service.handlers

import io.github.magonxesp.bus.domain.command.CommandHandler
import io.github.magonxesp.example.model.UserSaveCommand
import io.github.magonxesp.example.repository.UserRepository
import io.github.magonxesp.example.service.UserService
import org.slf4j.LoggerFactory

class UserSaveCommandHandler(private val service: UserService) : CommandHandler<UserSaveCommand> {
	override fun handle(command: UserSaveCommand) {
		service.save(command)
	}
}
