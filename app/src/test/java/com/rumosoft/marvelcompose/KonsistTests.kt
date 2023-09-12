package com.rumosoft.marvelcompose

import androidx.lifecycle.ViewModel
import com.lemonappdev.konsist.api.Konsist
import com.lemonappdev.konsist.api.ext.list.containingFiles
import com.lemonappdev.konsist.api.ext.list.imports
import com.lemonappdev.konsist.api.ext.list.withAllParentsOf
import com.lemonappdev.konsist.api.ext.list.withNameContaining
import com.lemonappdev.konsist.api.ext.list.withNameEndingWith
import com.lemonappdev.konsist.api.verify.assert
import com.lemonappdev.konsist.api.verify.assertNot
import org.junit.jupiter.api.Test

class KonsistTests {

    @Test
    fun `classes extending 'ViewModel' should have 'ViewModel' suffix`() {
        Konsist.scopeFromProject()
            .classes()
            .withAllParentsOf(ViewModel::class)
            .assert { it.name.endsWith("ViewModel") }
    }

    @Test
    fun `classes with 'UseCase' suffix should reside in 'usecase' package`() {
        Konsist.scopeFromProject()
            .classes()
            .withNameEndingWith("UseCase")
            .assert { it.resideInPackage("..usecase..") }
    }

    @Test
    fun `Usecases should have a public method named 'invoke'`() {
        Konsist
            .scopeFromProject()
            .classes()
            .withNameEndingWith("UseCase")
            .assert {
                it.containsFunction { function ->
                    function.name == "invoke" && function.hasPublicOrDefaultModifier
                }
            }
    }

    @Test
    fun `'Repository' classes should reside in 'data' package`() {
        Konsist
            .scopeFromProject()
            .classes()
            .withNameEndingWith("RepositoryImpl")
            .assert { it.resideInPackage("..data..") }
    }

    @Test
    fun `imports in use cases should not contain android`() {
        Konsist.scopeFromProject()
            .classes()
            .withNameContaining("UseCase")
            .containingFiles
            .imports
            .assertNot { it.hasNameContaining("android") }
    }
}
