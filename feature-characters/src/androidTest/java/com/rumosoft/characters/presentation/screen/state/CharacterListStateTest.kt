package com.rumosoft.characters.presentation.screen.state

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.karumi.shot.ScreenshotTest
import com.rumosoft.characters.infrastructure.sampleData.SampleData
import com.rumosoft.characters.presentation.viewmodel.state.HeroListErrorResult
import com.rumosoft.characters.presentation.viewmodel.state.HeroListNoResults
import com.rumosoft.characters.presentation.viewmodel.state.HeroListProgressIndicator
import com.rumosoft.characters.presentation.viewmodel.state.HeroListState
import com.rumosoft.characters.presentation.viewmodel.state.HeroListSuccessResult
import com.rumosoft.components.presentation.component.RetryButton
import com.rumosoft.components.presentation.theme.LocalLottieAnimationIterations
import com.rumosoft.components.presentation.theme.LottieAnimationIterations
import com.rumosoft.components.presentation.theme.MarvelComposeTheme
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

internal class CharacterListStateTest : ScreenshotTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun heroListResult_loading_shows_ProgressIndicator() {
        composeTestRule.setContent {
            CompositionLocalProvider(
                LocalLottieAnimationIterations provides LottieAnimationIterations(
                    1,
                ),
            ) {
                MarvelComposeTheme {
                    HeroListState.Loading.BuildUI()
                }
            }
        }

        composeTestRule.onNodeWithTag(HeroListProgressIndicator).assertIsDisplayed()
    }

    @Test
    fun heroListResult_error_shows_error_result() {
        composeTestRule.setContent {
            MarvelComposeTheme {
                HeroListState.Error(Exception("Error")).BuildUI()
            }
        }

        composeTestRule.onNodeWithTag(HeroListErrorResult).assertIsDisplayed()

        compareScreenshot(composeTestRule)
    }

    @Test
    fun heroListResult_error_tap_on_retry_executes_lambda() {
        var retried = false
        val retryFun: () -> Unit = { retried = true }
        composeTestRule.setContent {
            MarvelComposeTheme {
                HeroListState.Error(Exception("Error"), retryFun).BuildUI()
            }
        }

        composeTestRule.onNodeWithTag(RetryButton).performClick()
        assertTrue(retried)
    }

    @Test
    fun heroListResult_success_shows_success_result() {
        composeTestRule.setContent {
            MarvelComposeTheme {
                HeroListState.Success(SampleData.heroesSampleWithoutImages).BuildUI()
            }
        }

        composeTestRule.onNodeWithTag(HeroListSuccessResult).assertIsDisplayed()

        compareScreenshot(composeTestRule)
    }

    @Test
    fun heroListResult_success_with_no_data_shows_no_results() {
        composeTestRule.setContent {
            MarvelComposeTheme {
                HeroListState.Success(emptyList()).BuildUI()
            }
        }

        composeTestRule.onNodeWithTag(HeroListNoResults).assertIsDisplayed()

        compareScreenshot(composeTestRule)
    }
}
