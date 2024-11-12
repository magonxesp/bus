package io.github.magonxesp.example.routes

import io.github.magonxesp.bus.domain.command.CommandBus
import io.github.magonxesp.example.model.BookOffer
import io.github.magonxesp.example.model.BookOfferCreateCommand
import io.github.magonxesp.example.repository.BookOfferRepository
import io.github.magonxesp.example.service.BookOfferService
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.java.KoinJavaComponent.inject
import java.util.*

fun Routing.bookOfferRoutes() {
	val repository: BookOfferRepository by inject(BookOfferRepository::class.java)
	val commandBus: CommandBus by inject(CommandBus::class.java)

	get("/offers/{book_id}") {
		val id = call.parameters["book_id"]?.let { UUID.fromString(it) } ?: throw IllegalArgumentException("Invalid ID")
		val offers = repository.findByBook(id)

		call.respond(HttpStatusCode.OK, offers)
	}

	post("/offer") {
		val offer = call.receive<BookOffer>()
		commandBus.dispatch(BookOfferCreateCommand(data = offer))
		call.respond(HttpStatusCode.OK)
	}
}
