package io.github.magonxesp.bus

import io.github.magonxesp.bus.domain.command.Command
import io.github.magonxesp.bus.domain.command.CommandHandler
import io.github.magonxesp.bus.domain.event.DomainEvent
import io.github.magonxesp.bus.domain.event.DomainEventSubscriber
import io.github.magonxesp.bus.infrastructure.command.ReflectionCommandRegistry
import io.github.magonxesp.bus.infrastructure.event.ReflectionDomainEventRegistry
import io.kotest.core.spec.style.FunSpec
import io.kotest.core.test.TestCase
import io.kotest.core.test.TestResult
import kotlinx.datetime.Clock
import kotlin.io.path.Path
import kotlin.io.path.exists
import kotlin.io.path.writeText
import kotlin.reflect.KClass

abstract class IntegrationTestCase : FunSpec() {
	companion object {
		const val TEST_TMP_DIR = "test_tmp"
		val TEST_RUN_TIMESTAMP = Clock.System.now().epochSeconds.toString()

		fun DomainEventSubscriber<*>.notifyConsumed(event: DomainEvent) {
			val directory = Path(TEST_TMP_DIR, TEST_RUN_TIMESTAMP, "event", event.eventId).toFile().apply { mkdirs() }
			Path(directory.path, this::class.qualifiedName!!).writeText("")
		}

		fun CommandHandler<*>.notifyConsumed(command: Command<*>) {
			val directory = Path(TEST_TMP_DIR, TEST_RUN_TIMESTAMP, "command", command::class.qualifiedName!!).toFile().apply { mkdirs() }
			Path(directory.path, this::class.qualifiedName!!).writeText("")
		}
	}

	/**
	 * When a test event subscriber is dispatched It will write a file, then we should
	 * check if the file exists for ensure the subscriber is fired
	 */
	protected fun DomainEvent.isConsumedBy(klass: KClass<*>): Boolean =
		Path(TEST_TMP_DIR, TEST_RUN_TIMESTAMP, "event", eventId, klass.qualifiedName!!).exists()

	/**
	 * When a test command handler is dispatched It will write a file, then we should
	 * check if the file exists for ensure the handler is fired
	 */
	protected fun Command<*>.isConsumedBy(klass: KClass<*>): Boolean =
		Path(TEST_TMP_DIR, TEST_RUN_TIMESTAMP, "command", this::class.qualifiedName!!, klass.qualifiedName!!).exists()

	protected val domainEventRegistry = ReflectionDomainEventRegistry("io.github.magonxesp.bus")
	protected val commandRegistry = ReflectionCommandRegistry("io.github.magonxesp.bus")

	override suspend fun beforeTest(testCase: TestCase) {
		super.beforeTest(testCase)
		Path(TEST_TMP_DIR, TEST_RUN_TIMESTAMP).toFile().takeUnless { it.exists() }?.mkdir()
	}

	override suspend fun afterTest(testCase: TestCase, result: TestResult) {
		super.afterTest(testCase, result)
		Path(TEST_TMP_DIR, TEST_RUN_TIMESTAMP).toFile().takeUnless { !it.exists() }?.deleteRecursively()
	}
}
