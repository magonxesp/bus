package io.github.magonxesp.bus.domain.shared

import io.github.magonxesp.bus.domain.shared.attribute.*

abstract class BusMessage {
	var _attributes: MutableAttributes = mutableMapOf()
		internal set

	val attributes: Attributes
		get() = _attributes.toMap()

	protected fun <T : Any> readOnlyAttribute(value: T) = ReadOnlyAttribute(_attributes, value)
	protected fun <T : Any> attribute(value: T) = WritableAttribute(_attributes, value)
	protected fun <T : Any> nullableAttribute(value: T? = null) = NullableWritableAttribute(_attributes, value)
}
