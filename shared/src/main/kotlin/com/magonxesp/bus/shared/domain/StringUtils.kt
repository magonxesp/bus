package com.magonxesp.bus.shared.domain

fun String.camelToSnakeCase(): String {
	val pattern = "(?<=.)[A-Z]".toRegex()
	return this.replace(pattern, "_$0").lowercase()
}

fun String.snakeToCamelCase(): String {
	val pattern = "_[a-z]".toRegex()
	return replace(pattern) { it.value.last().uppercase() }
}
