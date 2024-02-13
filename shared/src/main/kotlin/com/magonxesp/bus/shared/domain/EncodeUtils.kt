package com.magonxesp.bus.shared.domain

import java.util.*

fun String.encodeToBase64() =
	Base64.getEncoder().encode(this.toByteArray(Charsets.UTF_8)).decodeToString()
