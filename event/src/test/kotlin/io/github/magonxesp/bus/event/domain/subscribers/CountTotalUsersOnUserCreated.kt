package io.github.magonxesp.bus.event.domain.subscribers

import io.github.magonxesp.bus.event.domain.DomainEventSubscriber
import io.github.magonxesp.bus.event.domain.events.UserCreated

class CountTotalUsersOnUserCreated : DomainEventSubscriber<UserCreated> {
	override fun handle(event: UserCreated) {
		println("count new user")
	}
}
