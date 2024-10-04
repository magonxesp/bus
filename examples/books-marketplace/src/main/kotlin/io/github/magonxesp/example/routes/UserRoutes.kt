package io.github.magonxesp.example.routes

import io.github.magonxesp.bus.domain.command.CommandBus
import io.github.magonxesp.example.model.UserSaveCommand
import io.github.magonxesp.example.repository.UserRepository
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.java.KoinJavaComponent.inject
import java.util.*

fun Routing.userRoutes() {
	val repository: UserRepository by inject(UserRepository::class.java)
	val commandBus: CommandBus by inject(CommandBus::class.java)

	get("/users/{id}") {
		val id = call.parameters["id"]?.let { UUID.fromString(it) } ?: throw IllegalArgumentException("Invalid ID")
		val user = repository.findById(id)

		if (user != null) {
			call.respond(HttpStatusCode.OK, user)
		} else {
			call.respond(HttpStatusCode.NotFound)
		}
	}

	put("/user") {
		val user = call.receive<UserSaveCommand>()
		commandBus.dispatch(user)
		call.respond(HttpStatusCode.OK)
	}
}
