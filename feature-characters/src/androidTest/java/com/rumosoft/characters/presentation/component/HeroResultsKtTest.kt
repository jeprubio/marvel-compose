package com.rumosoft.characters.presentation.component

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.junit4.createComposeRule
import com.rumosoft.characters.presentation.component.semantics.NumColumnsKey
import com.rumosoft.commons.domain.model.Character
import junitparams.JUnitParamsRunner
import junitparams.Parameters
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(JUnitParamsRunner::class)
internal class HeroResultsKtTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    companion object {
        @JvmStatic
        @Parameterized.Parameters
        fun deviceWidthParameters() = listOf(
            arrayOf(300, 1),
            arrayOf(600, 2),
            arrayOf(800, 3),
        )
    }

    @Test
    @Parameters(method = "deviceWidthParameters")
    fun columns_depending_on_width(width: Int, expectedColumns: Int) {
        composeTestRule.setContent {
            val mobileConfig = configWithScreenWidth(width)
            CompositionLocalProvider(
                LocalConfiguration provides mobileConfig,
            ) {
                HeroResults(
                    characters = getSampleCharacters(),
                )
            }
        }

        composeTestRule
            .onNode(SemanticsMatcher.expectValue(NumColumnsKey, expectedColumns))
            .assertExists()
    }

    @Composable
    private fun configWithScreenWidth(width: Int) = Configuration().apply {
        screenWidthDp = width
    }

    private fun getSampleCharacters(): List<Character> =
        (1..5).map { characterId ->
            Character(
                name = "character $characterId",
                description = "description $characterId",
                thumbnail = "",
                links = listOf(),
                comics = listOf(),
            )
        }
}
