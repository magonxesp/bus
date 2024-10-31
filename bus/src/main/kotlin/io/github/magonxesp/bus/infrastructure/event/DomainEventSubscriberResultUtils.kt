package io.github.magonxesp.bus.infrastructure.event

import io.github.magonxesp.bus.domain.event.DomainEventSubscriberClass

fun Map<DomainEventSubscriberClass, Result<Unit>>.throwOnFirstFailure() {
	val failure = entries.firstOrNull() { it.value.isFailure }

	if (failure != null) {
		throw failure.value.exceptionOrNull() ?: Exception("${failure.key} failed without exception")
	}
}
