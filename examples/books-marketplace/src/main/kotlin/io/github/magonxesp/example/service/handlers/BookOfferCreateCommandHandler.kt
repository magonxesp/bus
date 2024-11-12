package io.github.magonxesp.example.service.handlers

import io.github.magonxesp.bus.domain.command.CommandHandler
import io.github.magonxesp.example.model.BookOfferCreateCommand
import io.github.magonxesp.example.service.BookOfferService

class BookOfferCreateCommandHandler(
	private val service: BookOfferService
) : CommandHandler<BookOfferCreateCommand> {
	override fun handle(command: BookOfferCreateCommand) {
		service.create(command.data)
	}
}
