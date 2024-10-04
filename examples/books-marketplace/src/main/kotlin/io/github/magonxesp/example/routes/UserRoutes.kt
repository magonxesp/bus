package io.github.magonxesp.example.routes

import io.github.magonxesp.bus.domain.command.CommandBus
import io.github.magonxesp.example.model.UserSaveCommand
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.java.KoinJavaComponent.inject

fun Routing.userRoutes() {
	val commandBus: CommandBus by inject(CommandBus::class.java)

	put("/user") {
		val user = call.receive<UserSaveCommand>()
		commandBus.dispatch(user)
		call.respond(HttpStatusCode.OK)
	}
}
