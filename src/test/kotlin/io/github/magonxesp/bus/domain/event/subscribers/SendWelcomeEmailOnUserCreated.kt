package io.github.magonxesp.bus.domain.event.subscribers

import io.github.magonxesp.bus.domain.event.DomainEventSubscriber
import io.github.magonxesp.bus.domain.event.events.UserCreated

class SendWelcomeEmailOnUserCreated : DomainEventSubscriber<UserCreated> {
	override fun handle(event: UserCreated) {
		println("send welcome email")
	}
}
