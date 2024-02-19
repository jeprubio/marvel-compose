package com.rumosoft.comics.screen

import androidx.compose.ui.test.junit4.createComposeRule
import org.junit.Rule
import org.junit.Test

class ComicDetailsScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun whenScreenIsLoadedItShouldShowTheCharacterName() {
        comicDetailsScreenRobot {
        } verify {
            comicIsDisplayed("comic 1")
        }
    }

    @Test
    fun whenBackIsPressedItShouldInvokeBackPressed() {
        comicDetailsScreenRobot {
            onBackPressed()
        } verify {
            backWasPressed()
        }
    }
}
