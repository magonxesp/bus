package io.github.magonxesp.bus.event.domain.subscribers

import io.github.magonxesp.bus.event.domain.DomainEventSubscriber
import io.github.magonxesp.bus.event.domain.events.UserCreated

class SendWelcomeEmailOnUserCreated : DomainEventSubscriber<UserCreated> {
	override fun handle(event: UserCreated) {
		println("send welcome email")
	}
}
