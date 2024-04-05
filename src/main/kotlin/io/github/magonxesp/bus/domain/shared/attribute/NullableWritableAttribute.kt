package io.github.magonxesp.bus.domain.shared.attribute

import kotlin.reflect.KProperty

class NullableWritableAttribute<T : Any>(
	private val attributes: MutableAttributes,
	private val initialValue: T? = null
) {
	operator fun getValue(thisRef: Any?, property: KProperty<*>): T? {
		return attributes[property.name] as T?
	}

	operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T?) {
		if (value != null) {
			attributes[property.name] = value
		} else {
			attributes.remove(property.name)
		}
	}

	operator fun provideDelegate(thisRef: Any?, property: KProperty<*>): NullableWritableAttribute<T> {
		if (initialValue != null) {
			attributes[property.name] = initialValue
		}

		return this
	}
}
