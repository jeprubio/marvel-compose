package com.rumosoft.comics.screen

import androidx.compose.ui.res.stringResource
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.rumosoft.comics.presentation.screen.ComicDetailsScreenContent
import com.rumosoft.comics.presentation.viewmodel.state.ComicDetailsState
import com.rumosoft.components.R
import com.rumosoft.components.presentation.theme.MarvelComposeTheme
import org.junit.Assert

fun comicDetailsScreenRobot(composeTestRule: ComposeContentTestRule, func: ComicDetailsScreenRobot.() -> Unit) =
    ComicDetailsScreenRobot(composeTestRule).apply { func() }

class ComicDetailsScreenRobot(private val composeTestRule: ComposeContentTestRule) {
    private lateinit var backContentDescription: String
    private var backPressed = false

    init {
        composeTestRule.setContent {
            backContentDescription = stringResource(id = R.string.go_back)
            MarvelComposeTheme {
                ComicDetailsScreenContent(
                    screenState = ComicDetailsState.Success(
                        comic = FakeComics.getSampleComics(1).first(),
                    ),
                    onBackPressed = { backPressed = true },
                )
            }
        }
    }

    fun onBackPressed() {
        composeTestRule.onNodeWithContentDescription(backContentDescription).performClick()
    }

    infix fun verify(func: DetailsScreenResult.() -> Unit) =
        DetailsScreenResult(composeTestRule, backPressed).apply { func() }
}

class DetailsScreenResult(
    private val composeTestRule: ComposeContentTestRule,
    private val backPressed: Boolean,
) {
    fun comicIsDisplayed(name: String) {
        composeTestRule.onNodeWithText(name).assertExists()
    }

    fun backWasPressed() {
        Assert.assertTrue(backPressed)
    }
}
