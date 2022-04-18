package com.rumosoft.components.presentation.component

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.karumi.shot.ScreenshotTest
import com.rumosoft.components.R
import com.rumosoft.components.presentation.theme.MarvelComposeTheme
import org.junit.Rule
import org.junit.Test

internal class MarvelImageKtTest : ScreenshotTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun marvelImage_placeholder_is_shown_if_empty_thumbnail() {
        composeTestRule.setContent {
            MarvelComposeTheme {
                MarvelImage(thumbnailUrl = "", noImage = R.drawable.img_no_image)
            }
        }

        composeTestRule.onNodeWithTag(Placeholder).assertExists()
        compareScreenshot(composeTestRule)
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
