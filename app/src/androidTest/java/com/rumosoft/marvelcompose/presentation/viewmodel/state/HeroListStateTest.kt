package com.rumosoft.marvelcompose.presentation.viewmodel.state

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.rumosoft.marvelcompose.infrastructure.sampleData.SampleData
import com.rumosoft.marvelcompose.presentation.theme.MarvelComposeTheme
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