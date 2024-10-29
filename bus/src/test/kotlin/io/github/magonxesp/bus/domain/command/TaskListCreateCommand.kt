package io.github.magonxesp.bus.domain.command

import kotlinx.serialization.Serializable

@Serializable
data class TaskListCreateRequest(
	val tasks: List<Task>
) {
	@Serializable
	data class Task(
		val title: String,
		val status: String,
		val description: String,
		val assignee: User
	)

	@Serializable
	data class User(
		val id: String
	)
}

@Serializable
data class TaskListCreateCommand(
	override val data: TaskListCreateRequest
) : Command<TaskListCreateRequest>()
