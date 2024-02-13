package com.magonxesp.bus.command.infrastructure

import com.magonxesp.bus.command.domain.Command
import com.magonxesp.bus.shared.domain.camelToSnakeCase
import com.magonxesp.bus.shared.infrastructure.createSharedMoshiInstance

class CommandDeserializer(private val registry: CommandRegistry) {
	private val moshi = createSharedMoshiInstance()

	fun deserialize(rawCommand: String): Command {
		/*val commands = registry.handlers.map { it.key }
		val commandName = moshi.readValue(rawCommand, CommandName::class.java)
		val commandClass = commands.singleOrNull { it.simpleName!!.camelToSnakeCase() == commandName.name }
			?: throw RuntimeException("Command class for command name ${commandName.name} not found")
		val type = moshi.typeFactory.constructParametricType(CommandJson::class.java, commandClass.java)
		val command = moshi.readValue(rawCommand, type) as CommandJson<Command>
		return command.attributes*/
		TODO("implement")
	}

}
