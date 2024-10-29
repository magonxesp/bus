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
		val items = createOrder.data.items.map {
			val offer = offerRepository.findById(it.data.offerId)
				?: error("The offer selected is not available")

			if (it.data.quantity <= 0) {
				error("The quantity must be greater than 0")
			}

			if (it.data.quantity > offer.stock) {
				error("Quantity is greater than stock available in the offer")
			}

			OrderItem(
				id = UUID.randomUUID(),
				offerId = it.data.offerId,
				quantity = it.data.quantity,
				bookId = offer.bookId,
				orderId = createOrder.data.id,
				totalPrice = offer.price * it.data.quantity
			)
		}

		val order = Order(
			id = createOrder.data.id,
			userId = createOrder.data.userId,
			total = items.sumOf { it.totalPrice }
		)

		repository.save(order)
		items.forEach { orderItemsRepository.save(it) }

		val event = OrderCreatedEvent(orderId = order.id)
		eventBus.publish(event)
	}
}
