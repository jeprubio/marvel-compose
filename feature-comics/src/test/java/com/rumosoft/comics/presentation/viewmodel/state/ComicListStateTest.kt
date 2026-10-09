package com.rumosoft.comics.presentation.viewmodel.state

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
import com.rumosoft.comics.infrastructure.sampleData.SampleData
import com.rumosoft.comics.presentation.screen.state.BuildUI
import com.rumosoft.comics.presentation.viewmodel.state.ComicListState.Companion.ErrorResult
import com.rumosoft.comics.presentation.viewmodel.state.ComicListState.Companion.NoResults
import com.rumosoft.comics.presentation.viewmodel.state.ComicListState.Companion.ProgressIndicator
import com.rumosoft.comics.presentation.viewmodel.state.ComicListState.Companion.RetryTag
import com.rumosoft.comics.presentation.viewmodel.state.ComicListState.Companion.SuccessResult
import com.rumosoft.components.presentation.component.LoadMoreErrorTag
import com.rumosoft.components.presentation.component.LoadMoreRetryTag
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
internal class ComicListScreenStateTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val scrollTimeoutMillis = 5_000L

    @Test
    fun comicListState_loading_shows_ProgressIndicator() {
        composeTestRule.setContent {
            CompositionLocalProvider(
                LocalLottieAnimationIterations provides LottieAnimationIterations(
                    1,
                ),
            ) {
                MarvelComposeTheme {
                    ComicListState.Loading.BuildUI()
                }
            }
        }

        composeTestRule.onNodeWithTag(ProgressIndicator).assertIsDisplayed()
    }

    @Test
    fun comicListState_error_shows_error_result() {
        composeTestRule.setContent {
            MarvelComposeTheme {
                ComicListState.Error(Exception("Error")).BuildUI(
                    onRetry = {}
                )
            }
        }

        composeTestRule.onNodeWithTag(ErrorResult).assertIsDisplayed()

        composeTestRule.onRoot().captureRoboImage()
    }

    @Test
    fun comicListState_error_tap_on_retry_executes_lambda() {
        var retried = false
        val retryFun: () -> Unit = { retried = true }
        composeTestRule.setContent {
            MarvelComposeTheme {
                ComicListState.Error(Exception("Error")).BuildUI(
                    onRetry = retryFun
                )
            }
        }

        composeTestRule.onNodeWithTag(RetryTag).performClick()
        assertTrue(retried)
    }

    @Test
    fun comicListState_success_shows_success_result() {
        composeTestRule.setContent {
            MarvelComposeTheme {
                ComicListState.Success(SampleData.comicsSampleWithoutImages()).BuildUI(
                    onComicClick = {},
                    onEndReached = {}
                )
            }
        }

        composeTestRule.onNodeWithTag(SuccessResult).assertIsDisplayed()

        composeTestRule.onRoot().captureRoboImage()
    }

    @Test
    fun comicListState_success_with_no_data_shows_no_results() {
        composeTestRule.setContent {
            MarvelComposeTheme {
                ComicListState.Success(emptyList()).BuildUI(
                    onComicClick = {},
                    onEndReached = {}
                )
            }
        }

        composeTestRule.onNodeWithTag(NoResults).assertIsDisplayed()

        composeTestRule.onRoot().captureRoboImage()
    }

    @Test
    fun comicListState_success_with_load_more_error_shows_list_and_inline_error() {
        composeTestRule.setContent {
            MarvelComposeTheme {
                ComicListState.Success(
                    comics = SampleData.comicsSampleWithoutImages(),
                    loadMoreError = Exception("page 2 failed"),
                ).BuildUI(
                    onComicClick = {},
                    onEndReached = {},
                    onRetry = {},
                )
            }
        }

        composeTestRule.onNodeWithTag(SuccessResult).assertIsDisplayed()
        scrollToElement(
            targetTag = LoadMoreRetryTag,
            containerTag = SuccessResult,
        )
        composeTestRule.onNodeWithTag(LoadMoreErrorTag).assertIsDisplayed()

        composeTestRule.onRoot().captureRoboImage()
    }

    @Test
    fun comicListState_success_with_load_more_error_tap_on_retry_executes_lambda() {
        var retried = false
        composeTestRule.setContent {
            MarvelComposeTheme {
                ComicListState.Success(
                    comics = SampleData.comicsSampleWithoutImages(),
                    loadMoreError = Exception("page 2 failed"),
                ).BuildUI(
                    onComicClick = {},
                    onEndReached = {},
                    onRetry = { retried = true },
                )
            }
        }

        scrollToElement(
            targetTag = LoadMoreRetryTag,
            containerTag = SuccessResult,
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
