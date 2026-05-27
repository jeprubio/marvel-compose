package com.rumosoft.comics.presentation.viewmodel.state

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import com.github.takahirom.roborazzi.RobolectricDeviceQualifiers
import com.github.takahirom.roborazzi.captureRoboImage
import com.rumosoft.comics.infrastructure.sampleData.SampleData
import com.rumosoft.comics.presentation.screen.state.BuildUI
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
internal class ComicDetailsStateTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun comicDetailsState_loading_shows_ProgressIndicator() {
        composeTestRule.setContent {
            CompositionLocalProvider(LocalLottieAnimationIterations provides LottieAnimationIterations(1)) {
                MarvelComposeTheme {
                    ComicDetailsState.Loading.BuildUI()
                }
            }
        }

        composeTestRule.onNodeWithTag(ComicDetailsProgressIndicator).assertIsDisplayed()
    }

    @Test
    fun comicDetailsState_error_shows_error_result() {
        composeTestRule.setContent {
            MarvelComposeTheme {
                ComicDetailsState.Error(Exception("Error")).BuildUI(
                    onRetry = {}
                )
            }
        }

        composeTestRule.onNodeWithTag(ComicDetailsErrorResult).assertIsDisplayed()

        composeTestRule.onRoot().captureRoboImage()
    }

    @Test
    fun comicDetailsState_error_tap_on_retry_executes_lambda() {
        var retried = false
        val retryFun: () -> Unit = { retried = true }
        composeTestRule.setContent {
            MarvelComposeTheme {
                ComicDetailsState.Error(Exception("Error")).BuildUI(
                    onRetry = retryFun
                )
            }
        }

        composeTestRule.onNodeWithTag(RetryButton).performClick()
        assertTrue(retried)
    }

    @Test
    fun comicDetailsState_success_shows_success_result() {
        composeTestRule.setContent {
            MarvelComposeTheme {
                ComicDetailsState.Success(SampleData.comicsSample.first()).BuildUI()
            }
        }

        composeTestRule.onNodeWithTag(ComicDetailsSuccessResult).assertIsDisplayed()

        composeTestRule.onRoot().captureRoboImage()
    }
}
