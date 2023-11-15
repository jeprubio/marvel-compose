package com.rumosoft.marvelcompose

import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import com.lemonappdev.konsist.api.Konsist
import com.lemonappdev.konsist.api.ext.list.containingFiles
import com.lemonappdev.konsist.api.ext.list.imports
import com.lemonappdev.konsist.api.ext.list.withAllParentsOf
import com.lemonappdev.konsist.api.ext.list.withAnnotationOf
import com.lemonappdev.konsist.api.ext.list.withNameContaining
import com.lemonappdev.konsist.api.ext.list.withNameEndingWith
import com.lemonappdev.konsist.api.verify.assertFalse
import com.lemonappdev.konsist.api.verify.assertTrue
import org.junit.jupiter.api.Test

class KonsistTests {

    @Test
    fun `classes extending 'ViewModel' should have 'ViewModel' suffix`() {
        Konsist.scopeFromProject()
            .classes()
            .withAllParentsOf(ViewModel::class)
            .assertTrue { it.name.endsWith("ViewModel") }
    }

    @Test
    fun `classes with 'UseCase' suffix should reside in 'usecase' package`() {
        Konsist.scopeFromProject()
            .classes()
            .withNameEndingWith("UseCase")
            .assertTrue { it.resideInPackage("..usecase..") }
    }

    @Test
    fun `Usecases should have a public method named 'invoke'`() {
        Konsist
            .scopeFromProject()
            .classes()
            .withNameEndingWith("UseCase")
            .assertTrue {
                it.hasFunction { function ->
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
            .assertTrue { it.resideInPackage("..data..") }
    }

    @Test
    fun `imports in use cases should not contain android`() {
        Konsist.scopeFromProject()
            .classes()
            .withNameContaining("UseCase")
            .containingFiles
            .imports
            .assertFalse { it.hasNameContaining("android") }
    }

    @Test
    fun `All JetPack Compose previews contain 'Preview' in method name`() {
        Konsist
            .scopeFromProject()
            .functions()
            .withAnnotationOf(Preview::class)
            .assertTrue {
                it.name.contains("Preview")
            }
    }
}
