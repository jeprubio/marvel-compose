package com.rumosoft.comics.presentation.viewmodel.state

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.karumi.shot.ScreenshotTest
import com.rumosoft.comics.infrastructure.sampleData.SampleData
import com.rumosoft.comics.presentation.screen.state.BuildUI
import com.rumosoft.components.presentation.component.RetryButton
import com.rumosoft.components.presentation.theme.LocalLottieAnimationIterations
import com.rumosoft.components.presentation.theme.LottieAnimationIterations
import com.rumosoft.components.presentation.theme.MarvelComposeTheme
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import org.junit.Rule
import org.junit.Test

@ExperimentalFoundationApi
internal class ComicDetailsStateTest : ScreenshotTest {

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
                ComicDetailsState.Error(Exception("Error")).BuildUI()
            }
        }

        composeTestRule.onNodeWithTag(ComicDetailsErrorResult).assertIsDisplayed()

        compareScreenshot(composeTestRule)
    }

    @Test
    fun comicDetailsState_error_tap_on_retry_executes_lambda() {
        val retryFun: () -> Unit = mockk()
        every { retryFun.invoke() } just Runs
        composeTestRule.setContent {
            MarvelComposeTheme {
                ComicDetailsState.Error(Exception("Error"), retryFun).BuildUI()
            }
        }

        composeTestRule.onNodeWithTag(RetryButton).performClick()
        verify { retryFun.invoke() }
    }

    @Test
    fun comicDetailsState_success_shows_success_result() {
        composeTestRule.setContent {
            MarvelComposeTheme {
                ComicDetailsState.Success(SampleData.comicsSample.first()).BuildUI()
            }
        }

        composeTestRule.onNodeWithTag(ComicDetailsSuccessResult).assertIsDisplayed()

        compareScreenshot(composeTestRule)
    }
}
