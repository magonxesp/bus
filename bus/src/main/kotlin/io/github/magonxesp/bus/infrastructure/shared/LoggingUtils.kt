package io.github.magonxesp.bus.infrastructure.shared

import org.slf4j.Logger
import org.slf4j.LoggerFactory

fun <T> Any.withTimeSpentLog(function: Function<T>, block: () -> T) {
	val logger: Logger = LoggerFactory.getLogger(this::class.java)
	val startTime = System.currentTimeMillis()
	block()
	val endTime = System.currentTimeMillis()
	logger.info("Call to $function took ${endTime - startTime} ms")
}
