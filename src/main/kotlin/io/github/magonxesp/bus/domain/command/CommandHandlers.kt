package io.github.magonxesp.bus.domain.command

import kotlin.reflect.KClass

/**
 * The map of available command handlers
 *
 * The key is the command class and the value is the command handler.
 */
typealias CommandHandlers = Map<KClass<*>, KClass<*>>
