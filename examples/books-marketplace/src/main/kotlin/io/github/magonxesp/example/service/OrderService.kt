package io.github.magonxesp.example.service

import io.github.magonxesp.bus.domain.event.DomainEventBus
import io.github.magonxesp.example.model.Book
import io.github.magonxesp.example.repository.BookRepository

class OrderService(
	private val repository: BookRepository,
	private val eventBus: DomainEventBus
) {
	fun create(book: Book) = repository.save(book)
	fun listAll() = repository.findAll()
}
