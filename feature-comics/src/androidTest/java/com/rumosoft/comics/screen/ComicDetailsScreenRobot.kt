package com.rumosoft.comics.screen

import androidx.annotation.StringRes
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.platform.app.InstrumentationRegistry
import com.rumosoft.comics.presentation.screen.ComicDetailsScreenContent
import com.rumosoft.comics.presentation.viewmodel.state.ComicDetailsState
import com.rumosoft.components.R
import com.rumosoft.components.presentation.theme.MarvelComposeTheme
import org.junit.Assert.assertTrue

fun comicDetailsScreenRobot(
    composeTestRule: ComposeContentTestRule,
    func: ComicDetailsScreenRobot.() -> Unit
) =
    ComicDetailsScreenRobot(composeTestRule).apply { func() }

class ComicDetailsScreenRobot(private val composeTestRule: ComposeContentTestRule) {
    private var backPressed = false

    init {
        composeTestRule.setContent {
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
        composeTestRule.onNodeWithContentDescription(getString(R.string.go_back)).performClick()
    }

    infix fun verify(func: DetailsScreenResult.() -> Unit) =
        DetailsScreenResult(composeTestRule, backPressed).apply { func() }
}

class DetailsScreenResult(
    private val composeTestRule: ComposeContentTestRule,
    private val backPressed: Boolean,
) {
    fun comicIsDisplayed(name: String) {
        composeTestRule.onNodeWithText(name).assertIsDisplayed()
    }

    fun backWasPressed() {
        assertTrue(backPressed)
    }
}

private fun getString(@StringRes stringResource: Int) =
    InstrumentationRegistry.getInstrumentation().targetContext.getString(stringResource)
