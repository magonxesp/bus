package io.github.magonxesp.example.service

import io.github.magonxesp.example.model.UserSaveRequest
import io.github.magonxesp.example.repository.UserRepository

class UserService(private val repository: UserRepository) {
	fun save(request: UserSaveRequest) {
		repository.save(request.toUser())
	}
}
