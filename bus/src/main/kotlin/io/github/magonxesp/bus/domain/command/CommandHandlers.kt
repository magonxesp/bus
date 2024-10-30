package io.github.magonxesp.bus.domain.command

import kotlin.reflect.KClass

typealias CommandClass = KClass<out Command<*>>
typealias CommandHandlerClass = KClass<out CommandHandler<*>>

/**
 * The map of available command handlers
 *
 * The key is the command class and the value is the command handler.
 */
typealias CommandHandlers = Map<CommandClass, CommandHandlerClass>
