package io.github.magonxesp.example.service

import io.github.magonxesp.example.model.UserSaveCommand
import io.github.magonxesp.example.repository.UserRepository

class UserService(private val repository: UserRepository) {
	fun save(command: UserSaveCommand) {
		repository.save(command.toUser())
	}
}
