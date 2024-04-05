package io.github.magonxesp.bus.domain.event.events

import io.github.magonxesp.bus.domain.event.DomainEvent

class UserCreated(
	id: String,
	name: String,
	email: String,
	avatarUrl: String
) : DomainEvent() {
	override val eventName = "user_created"

	val id: String by readOnlyAttribute(id)
	val name: String by readOnlyAttribute(name)
	val email: String by readOnlyAttribute(email)
	val avatarUrl: String by readOnlyAttribute(avatarUrl)
}