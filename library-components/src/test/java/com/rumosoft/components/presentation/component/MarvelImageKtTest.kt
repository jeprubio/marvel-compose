package com.rumosoft.components.presentation.component

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onRoot
import com.github.takahirom.roborazzi.captureRoboImage
import com.rumosoft.components.R
import com.rumosoft.components.presentation.theme.MarvelComposeTheme
import com.rumosoft.libraryTests.ScreenshotUtils
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

    @Test
    fun `marvelImage placeholder is shown if empty thumbnail`() {
        composeTestRule.setContent {
            MarvelComposeTheme {
                MarvelImage(thumbnailUrl = "", noImage = R.drawable.img_no_image)
            }
        }

        captureScreenshot()
        composeTestRule.onNodeWithTag(Placeholder).assertExists()
    }

    @Test
    fun `marvelImage image is shown if non empty thumbnail`() {
        composeTestRule.setContent {
            MarvelComposeTheme {
                MarvelImage(thumbnailUrl = "whatever")
            }
        }

        composeTestRule.onNodeWithTag(MarvelImage).assertExists()
    }

    private fun captureScreenshot() {
        composeTestRule.onRoot().captureRoboImage(ScreenshotUtils.getScreenshotName())
    }
}
