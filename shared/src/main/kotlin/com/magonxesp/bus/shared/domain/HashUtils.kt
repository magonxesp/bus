package com.magonxesp.bus.shared.domain

import jakarta.xml.bind.DatatypeConverter
import java.security.MessageDigest

fun String.toMd5Hash(): String {
	val bytesOfMessage = this.toByteArray(Charsets.UTF_8)
	val md = MessageDigest.getInstance("MD5")
	val digest = md.digest(bytesOfMessage)
	return DatatypeConverter.printHexBinary(digest).lowercase()
}
