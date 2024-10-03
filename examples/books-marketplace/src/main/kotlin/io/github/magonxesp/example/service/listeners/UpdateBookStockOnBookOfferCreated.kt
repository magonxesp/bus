package io.github.magonxesp.example.service.listeners

import io.github.magonxesp.bus.domain.event.DomainEventSubscriber
import io.github.magonxesp.example.model.BookOfferCreatedEvent
import io.github.magonxesp.example.repository.BookRepository

class UpdateBookStockOnBookOfferCreated(
	private val bookRepository: BookRepository
) : DomainEventSubscriber<BookOfferCreatedEvent> {
	override fun handle(event: BookOfferCreatedEvent) {
		val book = bookRepository.findById(event.bookId)

		if (book != null) {
			bookRepository.save(book.copy(stock = book.stock + event.stock))
		}
	}
}
