package com.rumosoft.marvelapi

import com.lemonappdev.konsist.api.Konsist
import com.lemonappdev.konsist.api.architecture.KoArchitectureCreator.assertArchitecture
import com.lemonappdev.konsist.api.architecture.Layer
import org.junit.jupiter.api.Test

class KonsistTests {
    @Test
    fun `clean architecture layers on library-marvelapi have correct dependencies`() {
        Konsist
            .scopeFromModule("library-marvelapi")
            .assertArchitecture {
                // Define layers
                val data = Layer("Data", "com.rumosoft.marvelapi.data..")

                // Define architecture assertions
                data.dependsOnNothing()
            }
    }
}
