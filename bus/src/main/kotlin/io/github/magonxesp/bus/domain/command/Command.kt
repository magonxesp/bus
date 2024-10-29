package io.github.magonxesp.bus.domain.command

abstract class Command<T> {
	abstract val data: T
	open val metatada: CommandMetadata = CommandMetadata()
}
