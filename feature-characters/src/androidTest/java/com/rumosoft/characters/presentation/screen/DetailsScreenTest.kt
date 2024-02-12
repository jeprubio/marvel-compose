package com.rumosoft.characters.presentation.screen

import androidx.compose.ui.test.junit4.createComposeRule
import org.junit.Rule
import org.junit.Test

class DetailsScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun whenScreenIsLoadedItShouldShowTheCharacterName() {
        detailsScreenRobot(composeTestRule) {
        } verify {
            characterIsDisplayed("character 1")
        }
    }

    @Test
    fun whenBackIsPressedItShouldInvokeBackPressed() {
        detailsScreenRobot(composeTestRule) {
            onBackPressed()
        } verify {
            backWasPressed()
        }
    }
}
