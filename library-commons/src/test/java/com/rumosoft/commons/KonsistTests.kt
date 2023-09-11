package com.rumosoft.commons

import com.lemonappdev.konsist.api.Konsist
import com.lemonappdev.konsist.api.architecture.KoArchitectureCreator.assertArchitecture
import com.lemonappdev.konsist.api.architecture.Layer
import org.junit.jupiter.api.Test

class KonsistTests {
    @Test
    fun `clean architecture layers on library-commons have correct dependencies`() {
        Konsist
            .scopeFromModule("library-commons")
            .assertArchitecture {
                // Define layers
                val domain = Layer("Domain", "com.rumosoft.commons.domain..")
                val data = Layer("Data", "com.rumosoft.commons.data..")

                // Define architecture assertions
                domain.dependsOnNothing()
                data.dependsOn(domain)
            }
    }
}
