package io.github.magonxesp.example.plugins

import io.github.magonxesp.example.routes.bookOfferRoutes
import io.github.magonxesp.example.routes.bookRoutes
import io.github.magonxesp.example.routes.userRoutes
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
		userRoutes()
		bookRoutes()
		bookOfferRoutes()
    }
}
