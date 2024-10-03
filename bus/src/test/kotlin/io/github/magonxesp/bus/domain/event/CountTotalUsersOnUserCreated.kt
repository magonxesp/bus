package io.github.magonxesp.bus.domain.event

import io.github.magonxesp.bus.IntegrationTestCase.Companion.notifyConsumed
import org.slf4j.LoggerFactory

class CountTotalUsersOnUserCreated : DomainEventSubscriber<UserCreated> {
	private val logger = LoggerFactory.getLogger(this::class.java)

	override fun handle(event: UserCreated) {
		logger.debug("Domain event subscriber {} fired", this::class)
		notifyConsumed(event)
		logger.debug("Domain event subscriber {} finish", this::class)
	}
}
