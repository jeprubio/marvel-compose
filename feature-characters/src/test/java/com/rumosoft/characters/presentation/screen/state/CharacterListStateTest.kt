package com.rumosoft.characters.presentation.screen.state

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.v2.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipeUp
import com.github.takahirom.roborazzi.RobolectricDeviceQualifiers
import com.github.takahirom.roborazzi.captureRoboImage
import com.rumosoft.characters.infrastructure.sampleData.SampleData
import com.rumosoft.characters.presentation.viewmodel.state.HeroListErrorResult
import com.rumosoft.characters.presentation.viewmodel.state.HeroListNoResults
import com.rumosoft.characters.presentation.viewmodel.state.HeroListProgressIndicator
import com.rumosoft.characters.presentation.viewmodel.state.HeroListState
import com.rumosoft.characters.presentation.viewmodel.state.HeroListSuccessResult
import com.rumosoft.components.presentation.component.LoadMoreErrorTag
import com.rumosoft.components.presentation.component.LoadMoreRetryTag
import com.rumosoft.components.presentation.component.RetryButton
import com.rumosoft.components.presentation.theme.LocalLottieAnimationIterations
import com.rumosoft.components.presentation.theme.LottieAnimationIterations
import com.rumosoft.components.presentation.theme.MarvelComposeTheme
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode

@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(qualifiers = RobolectricDeviceQualifiers.Pixel5)
internal class CharacterListStateTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val scrollTimeoutMillis = 5_000L

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
                HeroListState.Success(SampleData.charactersSampleWithoutImages()).BuildUI(
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

    @Test
    fun heroListResult_success_with_load_more_error_shows_list_and_inline_error() {
        composeTestRule.setContent {
            MarvelComposeTheme {
                HeroListState.Success(
                    characters = SampleData.charactersSampleWithoutImages(),
                    loadMoreError = Exception("page 2 failed"),
                ).BuildUI(
                    onCharacterClick = {},
                    onEndReached = {},
                    onRetry = {},
                )
            }
        }

        composeTestRule.onNodeWithTag(HeroListSuccessResult).assertIsDisplayed()
        scrollToElement(
            targetTag = LoadMoreRetryTag,
            containerTag = HeroListSuccessResult,
        )
        composeTestRule.onNodeWithTag(LoadMoreErrorTag).assertIsDisplayed()

        composeTestRule.onRoot().captureRoboImage()
    }

    @Test
    fun heroListResult_success_with_load_more_error_tap_on_retry_executes_lambda() {
        var retried = false
        composeTestRule.setContent {
            MarvelComposeTheme {
                HeroListState.Success(
                    characters = SampleData.charactersSampleWithoutImages(),
                    loadMoreError = Exception("page 2 failed"),
                ).BuildUI(
                    onCharacterClick = {},
                    onEndReached = {},
                    onRetry = { retried = true },
                )
            }
        }

        scrollToElement(
            targetTag = LoadMoreRetryTag,
            containerTag = HeroListSuccessResult,
        )
        composeTestRule.onNodeWithTag(LoadMoreRetryTag).performClick()
        assertTrue(retried)
    }

    private fun scrollToElement(
        targetTag: String,
        containerTag: String,
    ) {
        composeTestRule.waitUntil(timeoutMillis = scrollTimeoutMillis) {
            try {
                composeTestRule
                    .onNodeWithTag(containerTag)
                    .performTouchInput { swipeUp() }
                composeTestRule
                    .onNodeWithTag(targetTag)
                    .assertExists()
                true
            } catch (_: AssertionError) {
                false
            }
        }
    }
}
