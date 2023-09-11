package com.rumosoft.comics

import com.lemonappdev.konsist.api.Konsist
import com.lemonappdev.konsist.api.architecture.KoArchitectureCreator.assertArchitecture
import com.lemonappdev.konsist.api.architecture.Layer
import org.junit.jupiter.api.Test

class KonsistTests {
    @Test
    fun `clean architecture layers on feature-comics have correct dependencies`() {
        Konsist
            .scopeFromModule("feature-comics")
            .assertArchitecture {
                // Define layers
                val domain = Layer("Domain", "com.rumosoft.comics.domain..")
                val presentation = Layer("Presentation", "com.rumosoft.comics.presentation..")
                val data = Layer("Data", "com.rumosoft.comics.data..")

                // Define architecture assertions
                domain.dependsOnNothing()
                presentation.dependsOn(domain)
                data.dependsOn(domain)
            }
    }
}
