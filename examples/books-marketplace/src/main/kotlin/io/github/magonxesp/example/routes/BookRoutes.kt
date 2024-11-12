package io.github.magonxesp.example.routes

import io.github.magonxesp.bus.domain.command.CommandBus
import io.github.magonxesp.example.model.Book
import io.github.magonxesp.example.model.BookSaveCommand
import io.github.magonxesp.example.repository.BookRepository
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.java.KoinJavaComponent.inject
import java.util.*

fun Routing.bookRoutes() {
	val repository: BookRepository by inject(BookRepository::class.java)
	val commandBus: CommandBus by inject(CommandBus::class.java)

	get("/books") {
		val books = repository.findAll()
		call.respond(HttpStatusCode.OK, books)
	}

	get("/books/{id}") {
		val id = call.parameters["id"]?.let { UUID.fromString(it) } ?: throw IllegalArgumentException("Invalid ID")
		val book = repository.findById(id)

		if (book != null) {
			call.respond(HttpStatusCode.OK, book)
		} else {
			call.respond(HttpStatusCode.NotFound)
		}
	}

	put("/book") {
		val book = call.receive<Book>()
		commandBus.dispatch(BookSaveCommand(book))
		call.respond(HttpStatusCode.OK)
	}
}
