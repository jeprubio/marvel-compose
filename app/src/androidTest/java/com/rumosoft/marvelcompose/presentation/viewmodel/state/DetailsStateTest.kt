package com.rumosoft.marvelcompose.presentation.viewmodel.state

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.rumosoft.marvelcompose.infrastructure.sampleData.SampleData
import com.rumosoft.marvelcompose.presentation.theme.MarvelComposeTheme
import org.junit.Rule
import org.junit.Test

internal class DetailsStateTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun detailsState_loading_shows_ProgressIndicator() {
        composeTestRule.setContent {
            MarvelComposeTheme {
                DetailsState.Loading.BuildUI()
            }
        }

        composeTestRule.onNodeWithTag(ProgressIndicator).assertIsDisplayed()
    }

    @Test
    fun detailsState_success_shows_success_result() {
        composeTestRule.setContent {
            MarvelComposeTheme {
                DetailsState.Success(SampleData.batman).BuildUI()
            }
        }

        composeTestRule.onNodeWithTag(SuccessResult).assertIsDisplayed()
    }
}
