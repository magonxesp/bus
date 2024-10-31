package io.github.magonxesp.bus.domain.event

class UserCreated(override val attributes: Attributes): DomainEvent<UserCreated.Attributes>() {
	constructor(
		id: String,
		name: String,
		email: String,
		avatarUrl: String
	) : this(Attributes(
		id = id,
		name = name,
		email = email,
		avatarUrl = avatarUrl
	))

	data class Attributes(
		val id: String,
		val name: String,
		val email: String,
		val avatarUrl: String,
	)

	override val eventName = "user_created"
}
