package com.magonxesp.bus.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

class BusPlugin : Plugin<Project> {
	override fun apply(target: Project) {
		target.tasks.register(RegisterCommandBusCommands.TASK_NAME, RegisterCommandBusCommands::class.java)
	}
}
