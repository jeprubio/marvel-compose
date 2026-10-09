package com.rumosoft.characters.presentation.screen

import androidx.compose.ui.test.junit4.v2.createComposeRule
import org.junit.Rule
import org.junit.Test

class HeroListScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun whenScreenIsLoadedItShouldShowTheListOfCharacters() {
        heroListRobot {
        } verify {
            characterIsDisplayed("Character1")
        }
    }


    @Test
    fun withoutASwipeUpEndReachedIsNotInvoked() {
        heroListRobot {
        } verify {
            endIsNotReached()
        }
    }

    @Test
    fun whenASwipeUpIsPerformedEndReachedIsInvoked() {
        heroListRobot {
            scrollBottom()
        } verify {
            endIsReached()
        }
    }
}
