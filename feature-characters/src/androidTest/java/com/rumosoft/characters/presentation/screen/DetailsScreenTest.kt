package com.rumosoft.characters.presentation.screen

import androidx.compose.ui.test.junit4.v2.createComposeRule
import org.junit.Rule
import org.junit.Test

class DetailsScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun whenScreenIsLoadedItShouldShowTheCharacterName() {
        detailsScreenRobot {
        } verify {
            characterIsDisplayed("Character1")
        }
    }

    @Test
    fun whenBackIsPressedItShouldInvokeBackPressed() {
        detailsScreenRobot {
            onBackPressed()
        } verify {
            backWasPressed()
        }
    }
}
