package io.github.magonxesp.bus.domain.command

data class TaskListCreateCommand(
	val tasks: List<Task>
) : Command {
	data class Task(
		val title: String,
		val status: String,
		val description: String,
		val assignee: User
	)

	data class User(
		val id: String
	)
}
