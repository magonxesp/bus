package io.github.magonxesp.bus

import io.kotest.core.spec.Spec
import io.kotest.core.spec.style.FunSpec
import java.io.File

abstract class IntegrationTestCase : FunSpec() {
	companion object {
		const val TEST_TMP_DIR = "test_tmp"
	}

	override suspend fun beforeSpec(spec: Spec) {
		super.beforeSpec(spec)
		File(TEST_TMP_DIR).takeUnless { it.exists() }?.mkdir()
	}
}
