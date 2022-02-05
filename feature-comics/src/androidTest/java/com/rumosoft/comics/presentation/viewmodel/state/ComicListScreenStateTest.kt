package com.rumosoft.comics.presentation.viewmodel.state

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.karumi.shot.ScreenshotTest
import com.rumosoft.comics.infrastructure.sampleData.SampleData
import com.rumosoft.comics.presentation.viewmodel.state.ComicListState.Companion.ErrorResult
import com.rumosoft.comics.presentation.viewmodel.state.ComicListState.Companion.NoResults
import com.rumosoft.comics.presentation.viewmodel.state.ComicListState.Companion.ProgressIndicator
import com.rumosoft.comics.presentation.viewmodel.state.ComicListState.Companion.RetryTag
import com.rumosoft.comics.presentation.viewmodel.state.ComicListState.Companion.SuccessResult
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
internal class ComicListScreenStateTest : ScreenshotTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun comicListState_loading_shows_ProgressIndicator() {
        composeTestRule.setContent {
            CompositionLocalProvider(
                LocalLottieAnimationIterations provides LottieAnimationIterations(
                    1
                )
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
                ComicListState.Error(Exception("Error")).BuildUI()
            }
        }

        composeTestRule.onNodeWithTag(ErrorResult).assertIsDisplayed()

        compareScreenshot(composeTestRule)
    }

    @Test
    fun comicListState_error_tap_on_retry_executes_lambda() {
        val retryFun: () -> Unit = mockk()
        every { retryFun.invoke() } just Runs
        composeTestRule.setContent {
            MarvelComposeTheme {
                ComicListState.Error(Exception("Error"), retryFun).BuildUI()
            }
        }

        composeTestRule.onNodeWithTag(RetryTag).performClick()
        verify { retryFun.invoke() }
    }

    @Test
    fun comicListState_success_shows_success_result() {
        composeTestRule.setContent {
            MarvelComposeTheme {
                ComicListState.Success(SampleData.comicsSample).BuildUI()
            }
        }

        composeTestRule.onNodeWithTag(SuccessResult).assertIsDisplayed()

        compareScreenshot(composeTestRule)
    }

    @Test
    fun comicListState_success_with_no_data_shows_no_results() {
        composeTestRule.setContent {
            MarvelComposeTheme {
                ComicListState.Success(emptyList()).BuildUI()
            }
        }

        composeTestRule.onNodeWithTag(NoResults).assertIsDisplayed()

        compareScreenshot(composeTestRule)
    }
}
