package io.github.magonxesp.bus.domain.command

import kotlin.reflect.KClass

typealias CommandClass = KClass<out Command<*>>
typealias CommandHandlerClass = KClass<out CommandHandler<*>>
typealias CommandHandlers = Map<CommandClass, CommandHandlerClass>
