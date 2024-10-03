package io.github.magonxesp.bus.domain.shared

import java.util.*

fun String.encodeToBase64() =
	Base64.getEncoder().encode(this.toByteArray(Charsets.UTF_8)).decodeToString()
