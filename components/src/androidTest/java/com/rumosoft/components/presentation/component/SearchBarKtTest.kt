package com.rumosoft.components.presentation.component

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextReplacement
import androidx.compose.ui.text.input.TextFieldValue
import com.karumi.shot.ScreenshotTest
import com.rumosoft.components.presentation.theme.MarvelComposeTheme
import io.mockk.justRun
import io.mockk.mockk
import io.mockk.verify
import org.junit.Rule
import org.junit.Test

@ExperimentalComposeUiApi
internal class SearchBarKtTest : ScreenshotTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun searchBar_empty_does_not_display_trailingIcon() {
        composeTestRule.setContent {
            MarvelComposeTheme {
                val textState = remember { mutableStateOf(TextFieldValue("")) }
                SearchBar(state = textState)
            }
        }

        composeTestRule.onNodeWithTag(SearchBarTrailingIconTestTag).assertDoesNotExist()

        compareScreenshot(composeTestRule)
    }

    @Test
    fun searchBar_not_empty_displays_trailingIcon() {
        composeTestRule.setContent {
            MarvelComposeTheme {
                val textState = remember { mutableStateOf(TextFieldValue("whatever")) }
                SearchBar(state = textState)
            }
        }

        composeTestRule.onNodeWithTag(SearchBarTrailingIconTestTag).assertIsDisplayed()

        compareScreenshot(composeTestRule)
    }

    @Test
    fun searchBar_trailing_icon_cleans_text() {
        val hint = "hint"
        composeTestRule.setContent {
            MarvelComposeTheme {
                val textState = remember { mutableStateOf(TextFieldValue("whatever")) }
                SearchBar(
                    state = textState,
                    hint = hint,
                )
            }
        }

        composeTestRule.onNodeWithTag(SearchBarTrailingIconTestTag).assertIsDisplayed()
        composeTestRule.onNodeWithTag(SearchBarTrailingIconTestTag).performClick()
        composeTestRule.onNodeWithTag(TextFieldTestTag).assert(hasText(hint))

        compareScreenshot(composeTestRule)
    }

    @Test
    fun searchBar_onTextChanges_calls_lambda() {
        val initialText = "whatever"
        val newText = "something"
        val onValueChanged: (String) -> Unit = mockk()
        justRun { onValueChanged(any()) }
        composeTestRule.setContent {
            MarvelComposeTheme {
                val textState = remember { mutableStateOf(TextFieldValue(initialText)) }
                SearchBar(
                    state = textState,
                    onValueChanged = onValueChanged
                )
            }
        }

        composeTestRule.onNodeWithTag(TextFieldTestTag).performTextReplacement(newText)

        verify { onValueChanged(newText) }
    }

    @Test
    fun searchBar_leading_icon_calls_lambda() {
        val onLeadingClicked: () -> Unit = mockk()
        justRun { onLeadingClicked() }
        composeTestRule.setContent {
            MarvelComposeTheme {
                val textState = remember { mutableStateOf(TextFieldValue("whatever")) }
                SearchBar(
                    state = textState,
                    onLeadingClicked = onLeadingClicked
                )
            }
        }

        composeTestRule.onNodeWithTag(SearchBarLeadingIconTestTag).assertIsDisplayed()
        composeTestRule.onNodeWithTag(SearchBarLeadingIconTestTag).performClick()
        verify { onLeadingClicked() }
    }
}
