package io.github.magonxesp.bus.command.infrastructure

import io.github.magonxesp.bus.command.domain.Command
import io.github.magonxesp.bus.core.domain.camelToSnakeCase
import io.github.magonxesp.bus.core.infrastructure.createSharedMoshiInstance

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
