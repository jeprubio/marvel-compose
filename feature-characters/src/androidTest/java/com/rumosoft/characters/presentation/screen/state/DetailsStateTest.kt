package com.rumosoft.characters.presentation.screen.state

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.karumi.shot.ScreenshotTest
import com.rumosoft.characters.infrastructure.sampleData.SampleData
import com.rumosoft.characters.presentation.viewmodel.state.DetailsState
import com.rumosoft.characters.presentation.viewmodel.state.HeroListProgressIndicator
import com.rumosoft.characters.presentation.viewmodel.state.HeroListSuccessResult
import com.rumosoft.components.presentation.theme.LocalLottieAnimationIterations
import com.rumosoft.components.presentation.theme.LottieAnimationIterations
import com.rumosoft.components.presentation.theme.MarvelComposeTheme
import org.junit.Rule
import org.junit.Test

internal class DetailsStateTest : ScreenshotTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun detailsState_loading_shows_ProgressIndicator() {
        composeTestRule.setContent {
            CompositionLocalProvider(
                LocalLottieAnimationIterations provides LottieAnimationIterations(
                    1,
                ),
            ) {
                MarvelComposeTheme {
                    DetailsState.Loading.BuildUI {}
                }
            }
        }

        composeTestRule.onNodeWithTag(HeroListProgressIndicator).assertIsDisplayed()
    }

    @Test
    fun detailsState_success_shows_success_result() {
        composeTestRule.setContent {
            MarvelComposeTheme {
                DetailsState.Success(SampleData.heroesSampleWithoutImages.first()).BuildUI {}
            }
        }

        composeTestRule.onNodeWithTag(HeroListSuccessResult).assertIsDisplayed()

        compareScreenshot(composeTestRule)
    }
}
