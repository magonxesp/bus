package io.github.magonxesp.bus.domain.event.subscribers

import io.github.magonxesp.bus.IntegrationTestCase
import io.github.magonxesp.bus.domain.event.DomainEventSubscriber
import io.github.magonxesp.bus.domain.event.events.UserCreated
import java.io.File
import kotlin.io.path.Path

class CountTotalUsersOnUserCreated : DomainEventSubscriber<UserCreated> {
	override fun handle(event: UserCreated) {
		Path(IntegrationTestCase.TEST_TMP_DIR, event.eventId).toFile().run {
			writeText(this::class.toString())
		}
	}
}
