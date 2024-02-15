package com.rumosoft.marvelcompose.presentation

import androidx.compose.ui.test.ComposeTimeoutException
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipeUp
import androidx.test.core.app.launchActivity
import com.rumosoft.marvelcompose.presentation.navigation.NAVIGATION_HOST_TEST_TAG

fun charactersScreenRobot(composeTestRule: ComposeContentTestRule, func: CharactersScreenRobot.() -> Unit) =
    CharactersScreenRobot(composeTestRule).apply { func() }

class CharactersScreenRobot(private val composeTestRule: ComposeContentTestRule) {

    init {
        launchActivity<MainActivity>()
    }

    @OptIn(ExperimentalTestApi::class)
    fun scrollBottom() {
        composeTestRule.waitUntilExactlyOneExists(hasText("character 1"))
        composeTestRule.onNodeWithTag(NAVIGATION_HOST_TEST_TAG).performTouchInput { swipeUp() }
    }

    @OptIn(ExperimentalTestApi::class)
    fun clickOnCharacter(characterName: String) {
        composeTestRule.waitUntilExactlyOneExists(hasText("character 1"))
        composeTestRule.onNodeWithText(characterName).performClick()
    }

    infix fun verify(func: CharactersScreenResult.() -> Unit) =
        CharactersScreenResult(composeTestRule).apply { func() }
}

class CharactersScreenResult(
    private val composeTestRule: ComposeContentTestRule,
) {
    fun charactersAreDisplayed() {
        textIsDisplayed("Characters")
    }

    fun characterIsDisplayed(characterName: String) {
        textIsDisplayed(characterName)
    }

    fun moreCharactersLoaded() {
        (21..40).forEach { characterId ->
            try {
                textIsDisplayed("character $characterId")
                println("Found character $characterId")
                return
            } catch (e: ComposeTimeoutException) {
                AssertionError("Couldn't find characters from 21 to 40", e)
            }
        }
    }

    fun characterDetailsAreDisplayed(characterName: String) {
        textIsDisplayed(characterName)
        textIsDisplayed("Description")
        textIsDisplayed("Comics")
    }

    @OptIn(ExperimentalTestApi::class)
    fun textIsDisplayed(text: String) {
        composeTestRule.waitUntilExactlyOneExists(hasText(text))
    }
}
