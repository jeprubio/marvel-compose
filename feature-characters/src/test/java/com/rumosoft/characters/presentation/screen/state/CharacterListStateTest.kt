package com.rumosoft.characters.presentation.screen.state

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.takahirom.roborazzi.captureRoboImage
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
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode

@RunWith(AndroidJUnit4::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(sdk = [34])
internal class CharacterListStateTest {
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
                HeroListState.Error(Exception("Error")).BuildUI(
                    onRetry = {}
                )
            }
        }

        composeTestRule.onNodeWithTag(HeroListErrorResult).assertIsDisplayed()

        composeTestRule.onRoot().captureRoboImage()
    }

    @Test
    fun heroListResult_error_tap_on_retry_executes_lambda() {
        var retried = false
        val retryFun: () -> Unit = { retried = true }
        composeTestRule.setContent {
            MarvelComposeTheme {
                HeroListState.Error(Exception("Error")).BuildUI(
                    onRetry = retryFun
                )
            }
        }

        composeTestRule.onNodeWithTag(RetryButton).performClick()
        assertTrue(retried)
    }

    @Test
    fun heroListResult_success_shows_success_result() {
        composeTestRule.setContent {
            MarvelComposeTheme {
                HeroListState.Success(SampleData.heroesSampleWithoutImages).BuildUI(
                    onCharacterClick = {},
                    onEndReached = {}
                )
            }
        }

        composeTestRule.onNodeWithTag(HeroListSuccessResult).assertIsDisplayed()

        composeTestRule.onRoot().captureRoboImage()
    }

    @Test
    fun heroListResult_success_with_no_data_shows_no_results() {
        composeTestRule.setContent {
            MarvelComposeTheme {
                HeroListState.Success(emptyList()).BuildUI(
                    onCharacterClick = {},
                    onEndReached = {}
                )
            }
        }

        composeTestRule.onNodeWithTag(HeroListNoResults).assertIsDisplayed()

        composeTestRule.onRoot().captureRoboImage()
    }
}
