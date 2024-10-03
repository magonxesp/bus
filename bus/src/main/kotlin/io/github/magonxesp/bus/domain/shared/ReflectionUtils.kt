package io.github.magonxesp.bus.domain.shared

import kotlin.reflect.KClass
import kotlin.reflect.KFunction

inline fun <reified T : Annotation> getAnnotation(klass: KClass<*>): T {
	val annotation = klass.annotations
		.firstOrNull { it is T }
		?.let { it as T }

	return annotation ?: throw RuntimeException("The class $klass must have the ${T::class} annotation")
}

fun <T> KFunction<T>.getParameter(name: String) = parameters.single {
	it.name == name
}

