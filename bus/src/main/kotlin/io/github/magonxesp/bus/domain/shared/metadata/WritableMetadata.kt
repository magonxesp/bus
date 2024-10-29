package io.github.magonxesp.bus.domain.shared.metadata

import kotlin.reflect.KProperty

class WritableMetadata<T : MetadataValue>(
	private val metadata: MutableMetadata,
	private val initialValue: T?
) {
	operator fun getValue(thisRef: Any?, property: KProperty<*>): T? {
		if (!metadata.containsKey(property.name)) {
			throw RuntimeException("The property ${property.name} don't have value")
		}

		return metadata[property.name] as T?
	}

	operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T?) {
		metadata[property.name] = value
	}

	operator fun provideDelegate(thisRef: Any?, property: KProperty<*>): WritableMetadata<T> {
		metadata[property.name] = initialValue
		return this
	}
}
