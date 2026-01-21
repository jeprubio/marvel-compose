package com.rumosoft.comics.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipeUp
import com.rumosoft.comics.presentation.screen.ComicsScreenContent
import com.rumosoft.comics.presentation.screen.ComicsTopBar
import com.rumosoft.comics.presentation.viewmodel.state.ComicListState
import com.rumosoft.components.presentation.theme.MarvelComposeTheme

fun ComicsScreenTest.comicsScreenRobot(func: ComicsScreenRobot.() -> Unit) =
    ComicsScreenRobot(composeTestRule).apply { func() }

private const val END_REACHED_TEXT = "End Reached"

class ComicsScreenRobot(private val composeTestRule: ComposeContentTestRule) {

    init {
        composeTestRule.setContent {
            MarvelComposeTheme {
                var endReached by remember { mutableStateOf(false) }
                if (endReached) {
                    Text(END_REACHED_TEXT)
                }
                Scaffold(
                    topBar = {
                        ComicsTopBar()
                    }
                ) { innerPadding ->
                    Box(
                        modifier = Modifier.padding(innerPadding),
                    ) {
                        ComicsScreenContent(
                            comicListState = ComicListState.Success(
                                comics = FakeComics.getSampleComics(20),
                            ),
                            onComicClick = {},
                            onEndReached = { endReached = true },
                            onRetry = {}
                        )
                    }
                }
            }
        }
    }

    fun scrollBottom() {
        repeat(5) {
            composeTestRule.onNodeWithTag(ComicListState.SuccessResult).performTouchInput { swipeUp() }
        }
    }

    infix fun verify(func: ComicsScreenResultRobot.() -> Unit) =
        ComicsScreenResultRobot(composeTestRule).apply { func() }
}

class ComicsScreenResultRobot(private val composeTestRule: ComposeContentTestRule) {
    fun comicIsDisplayed(name: String) {
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
}
