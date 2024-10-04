package io.github.magonxesp.example.service

import io.github.magonxesp.bus.domain.event.DomainEventBus
import io.github.magonxesp.example.model.BookOffer
import io.github.magonxesp.example.model.BookOfferCreatedEvent
import io.github.magonxesp.example.repository.BookOfferRepository
import io.github.magonxesp.example.repository.BookRepository
import io.github.magonxesp.example.repository.OrderItemsRepository
import java.util.UUID

class BookOfferService(
	private val repository: BookOfferRepository,
	private val bookRepository: BookRepository,
	private val orderItemsRepository: OrderItemsRepository,
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

	fun setBookCheapestOffer(bookId: UUID) {
		val book = bookRepository.findById(bookId)
			?: error("Book with id $bookId not found")
		val offer = repository.findByBook(bookId)
			.filter { it.stock > 0 }
			.minByOrNull { it.price }

		if (offer != null) {
			bookRepository.save(
				book.copy(
					stock = offer.stock,
					price = offer.price,
					offerId = offer.id
				)
			)
		}
	}

	fun claimStockFromOrder(orderId: UUID) {
		val items = orderItemsRepository.findByOrderId(orderId)

		for (item in items) {
			val offer = repository.findById(item.offerId)
				?: error("Offer with id ${item.offerId} not exists")

			var stock = offer.stock - item.quantity

			if (stock < 0) {
				stock = 0
			}

			repository.save(offer.copy(stock = stock))
		}
	}
}
