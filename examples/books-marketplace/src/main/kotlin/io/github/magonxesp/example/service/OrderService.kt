package io.github.magonxesp.example.service

import io.github.magonxesp.bus.domain.event.DomainEventBus
import io.github.magonxesp.example.model.Order
import io.github.magonxesp.example.model.OrderCreateCommand
import io.github.magonxesp.example.model.OrderCreatedEvent
import io.github.magonxesp.example.model.OrderItem
import io.github.magonxesp.example.repository.BookOfferRepository
import io.github.magonxesp.example.repository.OrderItemsRepository
import io.github.magonxesp.example.repository.OrderRepository
import java.util.UUID

class OrderService(
	private val repository: OrderRepository,
	private val orderItemsRepository: OrderItemsRepository,
	private val offerRepository: BookOfferRepository,
	private val eventBus: DomainEventBus
) {
	fun create(createOrder: OrderCreateCommand) {
		val items = createOrder.items.map {
			val offer = offerRepository.findById(it.offerId)
				?: error("The offer selected is not available")

			if (it.quantity <= 0) {
				error("The quantity must be greater than 0")
			}

			if (it.quantity > offer.stock) {
				error("Quantity is greater than stock available in the offer")
			}

			OrderItem(
				id = UUID.randomUUID(),
				offerId = it.offerId,
				quantity = it.quantity,
				bookId = offer.bookId,
				orderId = createOrder.id,
				totalPrice = offer.price * it.quantity
			)
		}

		val order = Order(
			id = createOrder.id,
			userId = createOrder.userId,
			total = items.sumOf { it.totalPrice }
		)

		repository.save(order)
		items.forEach { orderItemsRepository.save(it) }

		val event = OrderCreatedEvent(orderId = order.id)
		eventBus.publish(event)
	}
}
