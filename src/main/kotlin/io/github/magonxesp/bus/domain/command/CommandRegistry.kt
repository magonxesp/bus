package io.github.magonxesp.bus.domain.command

import kotlin.reflect.KClass

interface CommandRegistry {
	/**
	 * Get the command handlers
	 */
	fun commandHandlers(): Map<KClass<*>, KClass<*>>
}
