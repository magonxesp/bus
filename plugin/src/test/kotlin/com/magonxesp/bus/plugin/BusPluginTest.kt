package com.magonxesp.bus.plugin

import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.instanceOf
import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder



class BusPluginTest : AnnotationSpec() {
	@Test
	fun `it should apply plugin to the project`() {
		val project: Project = ProjectBuilder.builder().build()
		project.pluginManager.apply("com.github.magonxesp.bus")
		project.tasks.getByName(RegisterCommandBusCommands.TASK_NAME) shouldBe instanceOf(RegisterCommandBusCommands::class)
	}

	@Test
	fun `it should scan commands`() {
		val project: Project = ProjectBuilder.builder().build()
		project.pluginManager.apply("com.github.magonxesp.bus")
		project.pluginManager.apply("org.jetbrains.kotlin.jvm")

		val task = project.tasks.getByName(RegisterCommandBusCommands.TASK_NAME) as RegisterCommandBusCommands
		task.scanCommands()
	}
}
