package com.rumosoft.components.presentation.component

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onRoot
import com.github.takahirom.roborazzi.ExperimentalRoborazziApi
import com.github.takahirom.roborazzi.InternalRoborazziApi
import com.github.takahirom.roborazzi.RoborazziContext
import com.github.takahirom.roborazzi.captureRoboImage
import com.rumosoft.components.R
import com.rumosoft.components.presentation.theme.MarvelComposeTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode

@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(sdk = [33])
internal class MarvelImageKtTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    init {
        setScreenshotFolder()
    }

    @OptIn(InternalRoborazziApi::class, ExperimentalRoborazziApi::class)
    private fun setScreenshotFolder() {
        RoborazziContext.setRuleOverrideOutputDirectory("screenshots")
    }

    @Test
    fun marvelImage_placeholder_is_shown_if_empty_thumbnail() {
        composeTestRule.setContent {
            MarvelComposeTheme {
                MarvelImage(thumbnailUrl = "", noImage = R.drawable.img_no_image)
            }
        }

        composeTestRule.onRoot().captureRoboImage()
        composeTestRule.onNodeWithTag(Placeholder).assertExists()
    }

    @Test
    fun marvelImage_image_is_shown_if_non_empty_thumbnail() {
        composeTestRule.setContent {
            MarvelComposeTheme {
                MarvelImage(thumbnailUrl = "whatever")
            }
        }

        composeTestRule.onNodeWithTag(MarvelImage).assertExists()
    }
}
