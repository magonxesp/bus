package io.github.magonxesp.example.service

import io.github.magonxesp.bus.domain.event.DomainEventBus
import io.github.magonxesp.example.model.BookOffer
import io.github.magonxesp.example.model.BookOfferCreatedEvent
import io.github.magonxesp.example.repository.BookOfferRepository

class BookOfferService(
	private val repository: BookOfferRepository,
	private val eventBus: DomainEventBus
) {
	fun create(offer: BookOffer) {
		repository.save(offer)

		val event = BookOfferCreatedEvent(
			offerId = offer.id,
			bookId = offer.bookId,
			stock = offer.stock
		)

		eventBus.publish(event)
	}
}
