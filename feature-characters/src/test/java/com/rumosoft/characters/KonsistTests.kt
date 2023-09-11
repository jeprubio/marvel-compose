package com.rumosoft.characters

import com.lemonappdev.konsist.api.Konsist
import com.lemonappdev.konsist.api.architecture.KoArchitectureCreator.assertArchitecture
import com.lemonappdev.konsist.api.architecture.Layer
import org.junit.jupiter.api.Test

class KonsistTests {
    @Test
    fun `clean architecture layers on feature-characters have correct dependencies`() {
        Konsist
            .scopeFromModule("feature-characters")
            .assertArchitecture {
                // Define layers
                val domain = Layer("Domain", "com.rumosoft.characters.domain..")
                val presentation = Layer("Presentation", "com.rumosoft.characters.presentation..")
                val data = Layer("Data", "com.rumosoft.characters.data..")

                // Define architecture assertions
                domain.dependsOnNothing()
                presentation.dependsOn(domain)
                data.dependsOn(domain)
            }
    }
}
