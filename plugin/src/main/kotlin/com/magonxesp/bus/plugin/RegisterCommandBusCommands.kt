package com.magonxesp.bus.plugin

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension

abstract class RegisterCommandBusCommands : DefaultTask() {

	@TaskAction
	fun scanCommands() {
		val extension = project.extensions.getByType(KotlinJvmProjectExtension::class.java)
		val sourceSets = extension.sourceSets
		sourceSets.forEach { logger.info(it.name) }
	}

	companion object {
		const val TASK_NAME = "registerCommandBusCommands"
	}

}
