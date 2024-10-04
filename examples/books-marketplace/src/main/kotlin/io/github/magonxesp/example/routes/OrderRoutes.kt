package io.github.magonxesp.example.routes

import io.github.magonxesp.bus.domain.command.CommandBus
import io.github.magonxesp.example.model.OrderCreateCommand
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.java.KoinJavaComponent.inject

fun Routing.orderRoutes() {
	val commandBus: CommandBus by inject(CommandBus::class.java)

	post("/order") {
		commandBus.dispatch(call.receive<OrderCreateCommand>())
		call.respond(HttpStatusCode.OK)
	}
}
