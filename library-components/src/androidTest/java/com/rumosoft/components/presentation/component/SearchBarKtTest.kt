package com.rumosoft.components.presentation.component

import androidx.compose.ui.res.stringResource
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextReplacement
import com.karumi.shot.ScreenshotTest
import com.rumosoft.components.R
import com.rumosoft.components.presentation.theme.MarvelComposeTheme
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

internal class SearchBarKtTest : ScreenshotTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun searchBar_empty_does_not_display_clearIcon() {
        lateinit var clearContentDescription: String
        composeTestRule.setContent {
            clearContentDescription = stringResource(id = R.string.search_clear)
            MarvelComposeTheme {
                SearchBar(search = "")
            }
        }

        composeTestRule.onNodeWithContentDescription(clearContentDescription).assertDoesNotExist()

        compareScreenshot(composeTestRule)
    }

    @Test
    fun searchBar_not_empty_displays_clearIcon() {
        lateinit var clearContentDescription: String
        composeTestRule.setContent {
            clearContentDescription = stringResource(id = R.string.search_clear)
            MarvelComposeTheme {
                SearchBar(search = "whatever")
            }
        }

        composeTestRule.onNodeWithContentDescription(clearContentDescription).assertIsDisplayed()

        compareScreenshot(composeTestRule)
    }

    @Test
    fun searchBar_onTextChanges_calls_lambda() {
        val initialText = "whatever"
        val newText = "something"
        var onValueChangedInvoked = false
        val onValueChanged: (String) -> Unit = { _ ->
            onValueChangedInvoked = true
        }
        lateinit var searchTextContentDescription: String
        composeTestRule.setContent {
            searchTextContentDescription = stringResource(id = R.string.search_text)
            MarvelComposeTheme {
                SearchBar(
                    search = initialText,
                    onValueChanged = onValueChanged,
                )
            }
        }

        composeTestRule.onNodeWithContentDescription(searchTextContentDescription)
            .performTextReplacement(newText)

        assertTrue(onValueChangedInvoked)
    }

    @Test
    fun searchBar_leading_icon_calls_lambda() {
        var onLeadingClickedInvoked = false
        val onLeadingClicked: () -> Unit = { onLeadingClickedInvoked = true }
        lateinit var closeContentDescription: String
        composeTestRule.setContent {
            closeContentDescription = stringResource(id = R.string.search_close)
            MarvelComposeTheme {
                SearchBar(
                    search = "whatever",
                    onLeadingClicked = onLeadingClicked,
                )
            }
        }

        composeTestRule.onNodeWithContentDescription(closeContentDescription).assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription(closeContentDescription).performClick()

        assertTrue(onLeadingClickedInvoked)
    }
}
