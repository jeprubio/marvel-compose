package com.rumosoft.comics.screen

import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
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
import com.rumosoft.comics.presentation.screen.ComicsScreenContent
import com.rumosoft.comics.presentation.viewmodel.state.ComicListState
import com.rumosoft.components.R
import com.rumosoft.components.presentation.theme.MarvelComposeTheme

fun comicsScreenRobot(composeTestRule: ComposeContentTestRule, func: ComicsScreenRobot.() -> Unit) =
    ComicsScreenRobot(composeTestRule).apply { func() }

private const val END_REACHED_TEXT = "End Reached"
class ComicsScreenRobot(private val composeTestRule: ComposeContentTestRule) {
    private lateinit var searchContentDescription: String
    private lateinit var searchTextContentDescription: String

    init {
        composeTestRule.setContent {
            MarvelComposeTheme {
                val searchState = remember { mutableStateOf(TextFieldValue("")) }
                var showSearchBar by remember { mutableStateOf(false) }
                var endReached by remember { mutableStateOf(false) }
                searchContentDescription = stringResource(id = R.string.search)
                searchTextContentDescription = stringResource(id = R.string.search_text)
                if (endReached) {
                    Text(END_REACHED_TEXT)
                }
                ComicsScreenContent(
                    comicListState = ComicListState.Success(
                        comics = FakeComics.getSampleComics(20),
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

    fun onComicSearched(searchText: String) {
        onMagnifierTapped()
        composeTestRule.onNodeWithContentDescription(searchTextContentDescription)
            .performClick()
            .performTextInput(searchText)
    }

    fun onMagnifierTapped() {
        composeTestRule.onNodeWithContentDescription(searchContentDescription).performClick()
    }

    infix fun verify(func: ComicsScreenResultRobot.() -> Unit) =
        ComicsScreenResultRobot(composeTestRule).apply { func() }
}

class ComicsScreenResultRobot(private val composeTestRule: ComposeContentTestRule) {
    fun comicIsDisplayed(name: String) {
        composeTestRule.onNodeWithText(name).assertExists()
    }

    fun searchIsDisplayed() {
        val searchTextContentDescription =
            InstrumentationRegistry.getInstrumentation().targetContext.getString(R.string.search_text)
        composeTestRule.onNodeWithContentDescription(searchTextContentDescription).assertExists()
    }

    fun textIsSearched(name: String) {
        composeTestRule.onNodeWithText(name).assertExists()
    }

    fun endIsReached() {
        composeTestRule.onNodeWithText(END_REACHED_TEXT).assertExists()
    }

    fun endIsNotReached() {
        composeTestRule.onNodeWithText(END_REACHED_TEXT).assertDoesNotExist()
    }
}
