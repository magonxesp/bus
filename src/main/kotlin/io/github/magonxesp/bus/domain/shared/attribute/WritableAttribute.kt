package io.github.magonxesp.bus.domain.shared.attribute

import kotlin.reflect.KProperty

class WritableAttribute<T : Any>(
	private val attributes: MutableAttributes,
	private val initialValue: T
) {
	operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
		if (!attributes.containsKey(property.name)) {
			throw RuntimeException("The property ${property.name} don't have value")
		}

		return attributes[property.name] as T
	}

	operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
		attributes[property.name] = value
	}

	operator fun provideDelegate(thisRef: Any?, property: KProperty<*>): WritableAttribute<T> {
		attributes[property.name] = initialValue
		return this
	}
}
