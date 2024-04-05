package io.github.magonxesp.bus.domain.shared.attribute

import kotlin.reflect.KProperty

class ReadOnlyAttribute<T : AttributeValue>(
	private val attributes: MutableAttributes,
	private val initialValue: T
) {
	operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
		if (!attributes.containsKey(property.name)) {
			throw RuntimeException("The property ${property.name} don't have value")
		}

		return attributes[property.name]!! as T
	}

	operator fun provideDelegate(thisRef: Any?, property: KProperty<*>): ReadOnlyAttribute<T> {
		attributes[property.name] = initialValue
		return this
	}
}
