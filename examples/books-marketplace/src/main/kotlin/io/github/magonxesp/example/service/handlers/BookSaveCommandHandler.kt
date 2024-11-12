package io.github.magonxesp.example.service.handlers

import io.github.magonxesp.bus.domain.command.CommandHandler
import io.github.magonxesp.example.model.BookSaveCommand
import io.github.magonxesp.example.repository.BookRepository

class BookSaveCommandHandler(private val repository: BookRepository) : CommandHandler<BookSaveCommand> {
	override fun handle(command: BookSaveCommand) {
		repository.save(command.data)
	}
}
