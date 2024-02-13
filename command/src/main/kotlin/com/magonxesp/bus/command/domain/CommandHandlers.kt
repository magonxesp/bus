package com.magonxesp.bus.command.domain

import kotlin.reflect.KClass

typealias CommandHandlers = Map<KClass<*>, KClass<*>>
