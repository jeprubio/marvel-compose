package com.rumosoft.characters.presentation.screen

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.platform.app.InstrumentationRegistry
import com.rumosoft.characters.presentation.FakeCharacters
import com.rumosoft.characters.presentation.viewmodel.state.DetailsState
import com.rumosoft.components.R
import com.rumosoft.components.presentation.theme.MarvelComposeTheme
import org.junit.Assert.assertTrue

fun DetailsScreenTest.detailsScreenRobot(
    func: DetailsScreenRobot.() -> Unit
) =
    DetailsScreenRobot(composeTestRule).apply { func() }

class DetailsScreenRobot(private val composeTestRule: ComposeContentTestRule) {
    private var backPressed = false

    init {
        composeTestRule.setContent {
            MarvelComposeTheme {
                Scaffold(
                    topBar = {
                        CharacterDetailsTopBar(
                            onBackPressed = { backPressed = true },
                        )
                    },
                ) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        DetailsScreenContent(
                            detailsState = DetailsState.Success(FakeCharacters.getSampleCharacters().first()),
                            onComicSelected = {},
                        )
                    }
                }
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
    fun characterIsDisplayed(name: String) {
        textIsDisplayed(name)
    }

    fun backWasPressed() {
        assertTrue(backPressed)
    }

    @OptIn(ExperimentalTestApi::class)
    fun textIsDisplayed(text: String) {
        composeTestRule.waitUntilExactlyOneExists(hasText(text))
        composeTestRule.onNodeWithText(text).assertIsDisplayed()
    }
}

private fun getString(@StringRes stringResource: Int) =
    InstrumentationRegistry.getInstrumentation().targetContext.getString(stringResource)
