package com.rumosoft.marvelcompose.presentation.viewmodel.state

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.rumosoft.feature_characters.infrastructure.sampleData.SampleData
import com.rumosoft.components.presentation.theme.MarvelComposeTheme
import com.rumosoft.feature_characters.presentation.viewmodel.state.ErrorResult
import com.rumosoft.feature_characters.presentation.viewmodel.state.HeroListState
import com.rumosoft.feature_characters.presentation.viewmodel.state.NoResults
import com.rumosoft.feature_characters.presentation.viewmodel.state.ProgressIndicator
import com.rumosoft.feature_characters.presentation.viewmodel.state.RetryTag
import com.rumosoft.feature_characters.presentation.viewmodel.state.SuccessResult
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import org.junit.Rule
import org.junit.Test

internal class HeroListStateTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun heroListResult_loading_shows_ProgressIndicator() {
        composeTestRule.setContent {
            MarvelComposeTheme {
                HeroListState.Loading.BuildUI()
            }
        }

        composeTestRule.onNodeWithTag(ProgressIndicator).assertIsDisplayed()
    }

    @Test
    fun heroListResult_error_shows_error_result() {
        composeTestRule.setContent {
            MarvelComposeTheme {
                HeroListState.Error(Exception("Error")).BuildUI()
            }
        }

        composeTestRule.onNodeWithTag(ErrorResult).assertIsDisplayed()
    }

    @Test
    fun heroListResult_error_tap_on_retry_executes_lambda() {
        val retryFun: () -> Unit = mockk()
        every { retryFun.invoke() } just Runs
        composeTestRule.setContent {
            MarvelComposeTheme {
                HeroListState.Error(Exception("Error"), retryFun).BuildUI()
            }
        }

        composeTestRule.onNodeWithTag(RetryTag).performClick()
        verify { retryFun.invoke() }
    }

    @Test
    fun heroListResult_success_shows_success_result() {
        composeTestRule.setContent {
            MarvelComposeTheme {
                HeroListState.Success(SampleData.heroesSample).BuildUI()
            }
        }

        composeTestRule.onNodeWithTag(SuccessResult).assertIsDisplayed()
    }

    @Test
    fun heroListResult_success_with_no_data_shows_no_results() {
        composeTestRule.setContent {
            MarvelComposeTheme {
                HeroListState.Success(emptyList()).BuildUI()
            }
        }

        composeTestRule.onNodeWithTag(NoResults).assertIsDisplayed()
    }
}
