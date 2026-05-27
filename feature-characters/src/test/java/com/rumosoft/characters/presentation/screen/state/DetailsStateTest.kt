package com.rumosoft.characters.presentation.screen.state

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.v2.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onRoot
import com.github.takahirom.roborazzi.RobolectricDeviceQualifiers
import com.github.takahirom.roborazzi.captureRoboImage
import com.rumosoft.characters.infrastructure.sampleData.SampleData
import com.rumosoft.characters.presentation.viewmodel.state.DetailsState
import com.rumosoft.characters.presentation.viewmodel.state.HeroListProgressIndicator
import com.rumosoft.characters.presentation.viewmodel.state.HeroListSuccessResult
import com.rumosoft.components.presentation.theme.LocalLottieAnimationIterations
import com.rumosoft.components.presentation.theme.LottieAnimationIterations
import com.rumosoft.components.presentation.theme.MarvelComposeTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode

@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(qualifiers = RobolectricDeviceQualifiers.Pixel5)
internal class DetailsStateTest {
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
                    DetailsState.Loading.BuildUI(onComicSelected = {})
                }
            }
        }

        composeTestRule.onNodeWithTag(HeroListProgressIndicator).assertIsDisplayed()
    }

    @Test
    fun detailsState_success_shows_success_result() {
        composeTestRule.setContent {
            MarvelComposeTheme {
                DetailsState.Success(SampleData.heroesSampleWithoutImages.first()).BuildUI(onComicSelected = {})
            }
        }

        composeTestRule.onNodeWithTag(HeroListSuccessResult).assertIsDisplayed()

        composeTestRule.onRoot().captureRoboImage()
    }
}
