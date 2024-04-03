package io.github.magonxesp.bus.event.domain.events

import io.github.magonxesp.bus.event.domain.DomainEvent

class UserCreated : DomainEvent() {
	override val eventName = "user_created"

	var id: String by attributes
		internal set
	var name: String by attributes
		internal set
	var email: String by attributes
		internal set
	var avatarUrl: String by attributes
		internal set
}
