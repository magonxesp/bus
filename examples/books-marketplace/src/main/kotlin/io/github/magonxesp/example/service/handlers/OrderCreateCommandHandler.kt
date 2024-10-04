package io.github.magonxesp.example.service.handlers

import io.github.magonxesp.bus.domain.command.CommandHandler
import io.github.magonxesp.example.model.OrderCreateCommand
import io.github.magonxesp.example.service.OrderService

class OrderCreateCommandHandler(private val service: OrderService) : CommandHandler<OrderCreateCommand> {
	override fun handle(command: OrderCreateCommand) {
		service.create(command)
	}
}
