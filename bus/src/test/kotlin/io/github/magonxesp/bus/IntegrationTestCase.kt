package io.github.magonxesp.bus

import io.kotest.core.spec.style.FunSpec
import io.kotest.core.test.TestCase

abstract class IntegrationTestCase : FunSpec() {
	override suspend fun beforeTest(testCase: TestCase) {
		super.beforeTest(testCase)

		commandConsumed = mutableMapOf()
		domainEventConsumed = mutableMapOf()
	}
}
