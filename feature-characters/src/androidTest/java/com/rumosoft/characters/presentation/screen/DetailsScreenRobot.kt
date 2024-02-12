package com.rumosoft.characters.presentation.screen

import androidx.compose.ui.res.stringResource
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.rumosoft.characters.presentation.FakeCharacters
import com.rumosoft.characters.presentation.viewmodel.state.DetailsState
import com.rumosoft.components.presentation.theme.MarvelComposeTheme
import org.junit.Assert.assertTrue

fun detailsScreenRobot(composeTestRule: ComposeContentTestRule, func: DetailsScreenRobot.() -> Unit) =
    DetailsScreenRobot(composeTestRule).apply { func() }

class DetailsScreenRobot(private val composeTestRule: ComposeContentTestRule) {
    
    private lateinit var backContentDescription: String
    private var backPressed = false

    init {
        composeTestRule.setContent {
            backContentDescription = stringResource(id = com.rumosoft.components.R.string.go_back)
            MarvelComposeTheme {
                DetailsScreenContent(
                    detailsState = DetailsState.Success(
                        character = FakeCharacters.getSampleCharacters(1).first(),
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
    fun characterIsDisplayed(name: String) {
        composeTestRule.onNodeWithText(name).assertExists()
    }

    fun backWasPressed() {
        assertTrue(backPressed)
    }
}
