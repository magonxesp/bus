package io.github.magonxesp.bus.domain.event

import io.github.magonxesp.bus.markAsConsumed
import org.slf4j.LoggerFactory

class CountTotalUsersOnUserCreated : DomainEventSubscriber<UserCreated> {
	private val logger = LoggerFactory.getLogger(this::class.java)

	override fun handle(event: UserCreated) {
		logger.debug("Domain event subscriber {} fired", this::class)
		event.markAsConsumed(this::class)
		logger.debug("Domain event subscriber {} finish", this::class)
	}
}
