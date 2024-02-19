package com.rumosoft.characters.presentation.screen

import androidx.annotation.StringRes
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipeUp
import androidx.compose.ui.text.input.TextFieldValue
import androidx.test.platform.app.InstrumentationRegistry
import com.rumosoft.characters.presentation.FakeCharacters
import com.rumosoft.characters.presentation.viewmodel.state.HeroListState
import com.rumosoft.components.R
import com.rumosoft.components.presentation.theme.MarvelComposeTheme

fun HeroListScreenTest.heroListRobot(func: HeroListRobot.() -> Unit) =
    HeroListRobot(composeTestRule).apply { func() }

private const val END_REACHED_TEXT = "End Reached"

class HeroListRobot(private val composeTestRule: ComposeContentTestRule) {

    init {
        composeTestRule.setContent {
            MarvelComposeTheme {
                val searchState = remember { mutableStateOf(TextFieldValue("")) }
                var showSearchBar by remember { mutableStateOf(false) }
                var endReached by remember { mutableStateOf(false) }
                if (endReached) {
                    Text(END_REACHED_TEXT)
                }
                HeroListScreenContent(
                    heroListState = HeroListState.Success(
                        characters = FakeCharacters.getSampleCharacters(10),
                        onEndReached = { endReached = true }
                    ),
                    showSearchBar = showSearchBar,
                    searchText = searchState,
                    onToggleSearchClick = { showSearchBar = true }
                )
            }
        }
    }

    fun scrollBottom() {
        composeTestRule.onRoot().performTouchInput { swipeUp() }
    }

    @OptIn(ExperimentalTestApi::class)
    fun onCharacterSearched(searchText: String) {
        onMagnifierTapped()
        composeTestRule.waitUntilExactlyOneExists(hasContentDescription(getString(R.string.search_text)))
        composeTestRule.onNodeWithContentDescription(getString(R.string.search_text))
            .performClick()
            .performTextInput(searchText)
    }

    fun onMagnifierTapped() {
        composeTestRule.onNodeWithContentDescription(getString(R.string.search)).performClick()
    }

    infix fun verify(func: HeroListResultRobot.() -> Unit) =
        HeroListResultRobot(composeTestRule).apply { func() }
}

class HeroListResultRobot(private val composeTestRule: ComposeContentTestRule) {
    fun characterIsDisplayed(name: String) {
        textIsDisplayed(name)
    }

    fun searchIsDisplayed() {
        contentDescriptionIsDisplayed(getString(R.string.search_text))
    }

    fun textIsSearched(name: String) {
        textIsDisplayed(name)
    }

    fun endIsReached() {
        composeTestRule.onNodeWithText(END_REACHED_TEXT).assertIsDisplayed()
    }

    fun endIsNotReached() {
        composeTestRule.onNodeWithText(END_REACHED_TEXT).assertDoesNotExist()
    }

    @OptIn(ExperimentalTestApi::class)
    fun textIsDisplayed(text: String) {
        composeTestRule.waitUntilExactlyOneExists(hasText(text))
        composeTestRule.onNodeWithText(text).assertIsDisplayed()
    }

    @OptIn(ExperimentalTestApi::class)
    fun contentDescriptionIsDisplayed(contentDescription: String) {
        composeTestRule.waitUntilExactlyOneExists(hasContentDescription(contentDescription))
        composeTestRule.onNodeWithContentDescription(contentDescription).assertIsDisplayed()
    }
}

private fun getString(@StringRes stringResource: Int) =
    InstrumentationRegistry.getInstrumentation().targetContext.getString(stringResource)
