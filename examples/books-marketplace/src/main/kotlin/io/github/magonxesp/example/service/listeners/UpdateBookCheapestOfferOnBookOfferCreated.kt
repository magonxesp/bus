package io.github.magonxesp.example.service.listeners

import io.github.magonxesp.bus.domain.event.DomainEventSubscriber
import io.github.magonxesp.example.model.BookOfferCreatedEvent
import io.github.magonxesp.example.service.BookOfferService

class UpdateBookCheapestOfferOnBookOfferCreated(private val service: BookOfferService) : DomainEventSubscriber<BookOfferCreatedEvent> {
	override fun handle(event: BookOfferCreatedEvent) {
		service.setBookCheapestOffer(event.bookId)
	}
}
