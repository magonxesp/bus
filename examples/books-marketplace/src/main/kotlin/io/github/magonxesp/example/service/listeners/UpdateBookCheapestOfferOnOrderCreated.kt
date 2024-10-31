package io.github.magonxesp.example.service.listeners

import io.github.magonxesp.bus.domain.event.DomainEventSubscriber
import io.github.magonxesp.example.model.BookOfferCreatedEvent
import io.github.magonxesp.example.model.OrderCreatedEvent
import io.github.magonxesp.example.repository.OrderItemsRepository
import io.github.magonxesp.example.service.BookOfferService

class UpdateBookCheapestOfferOnOrderCreated(
	private val service: BookOfferService,
	private val orderItemsRepository: OrderItemsRepository
) : DomainEventSubscriber<OrderCreatedEvent> {
	override fun handle(event: OrderCreatedEvent) {
		orderItemsRepository.findByOrderId(event.attributes.orderId).forEach {
			service.setBookCheapestOffer(it.bookId)
		}
	}
}
