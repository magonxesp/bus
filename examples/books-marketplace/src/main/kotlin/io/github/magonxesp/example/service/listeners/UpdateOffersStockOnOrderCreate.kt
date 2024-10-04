package io.github.magonxesp.example.service.listeners

import io.github.magonxesp.bus.domain.event.DomainEventSubscriber
import io.github.magonxesp.example.model.OrderCreatedEvent
import io.github.magonxesp.example.service.BookOfferService

class UpdateOffersStockOnOrderCreate(private val bookOfferService: BookOfferService) : DomainEventSubscriber<OrderCreatedEvent> {
	override fun handle(event: OrderCreatedEvent) {
		bookOfferService.claimStockFromOrder(event.orderId)
	}
}
