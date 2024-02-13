package com.magonxesp.bus.command.infrastructure

import com.magonxesp.bus.command.domain.Command
import com.magonxesp.bus.shared.domain.camelToSnakeCase
import com.magonxesp.bus.shared.infrastructure.createSharedMoshiInstance

class CommandSerializer {
	private val moshi = createSharedMoshiInstance()

	fun serialize(command: Command): String =
		moshi.adapter(CommandJson::class.java).toJson(
			CommandJson(
				name = command::class.simpleName!!.camelToSnakeCase(),
				attributes = command
			)
		)
}
