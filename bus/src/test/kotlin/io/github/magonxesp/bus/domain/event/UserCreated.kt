package io.github.magonxesp.bus.domain.event

class UserCreated(
	id: String,
	name: String,
	email: String,
	avatarUrl: String
) : DomainEvent() {
	constructor() : this(
		id = "",
		name = "",
		email = "",
		avatarUrl = "",
	)

	override val eventName = "user_created"

	val id: String by readOnlyAttribute(id)
	val name: String by readOnlyAttribute(name)
	val email: String by readOnlyAttribute(email)
	val avatarUrl: String by readOnlyAttribute(avatarUrl)
}
