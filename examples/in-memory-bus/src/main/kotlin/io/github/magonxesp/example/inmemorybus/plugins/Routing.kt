package io.github.magonxesp.example.inmemorybus.plugins

import io.github.magonxesp.example.inmemorybus.routes.bookRoutes
import io.github.magonxesp.example.inmemorybus.routes.userRoutes
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
		userRoutes()
		bookRoutes()
    }
}
