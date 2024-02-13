package com.magonxesp.bus.command.domain

import kotlin.reflect.KClass
import kotlin.reflect.KProperty

interface Command

/*
(protected val attributes: MutableMap<String, Any> = mutableMapOf()) {
    inner class Attribute {
        private fun isPrimitiveType(type: KClass<*>): Boolean {
            return when (type) {
                Byte::class,
                Short::class,
                Int::class,
                Long::class,
                Float::class,
                Double::class,
                Char::class,
                Boolean::class -> true
                else -> false
            }
        }

        operator fun getValue(thisRef: Any?, property: KProperty<*>): String {
            attributes[property.name]
            return "$thisRef, thank you for delegating '${property.name}' to me!"
        }

        operator fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
            println("$value has been assigned to '${property.name}' in $thisRef.")
        }
    }
}
 */
