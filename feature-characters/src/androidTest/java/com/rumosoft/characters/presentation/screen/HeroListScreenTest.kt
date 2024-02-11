package com.rumosoft.characters.presentation.screen

import androidx.compose.ui.test.junit4.createComposeRule
import org.junit.Rule
import org.junit.Test

class HeroListScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun whenScreenIsLoadedItShouldShowTheListOfCharacters() {
        heroListRobot(composeTestRule) {
        } verify {
            characterIsDisplayed("character 1")
        }
    }

    @Test
    fun whenMagnifierTappedItShowsSearchTextInput() {
        heroListRobot(composeTestRule) {
            onMagnifierTapped()
        } verify {
            searchIsDisplayed()
        }
    }

    @Test
    fun whenCharacterIsSearchedItsShownInSearchTextInput() {
        heroListRobot(composeTestRule) {
            onCharacterSearched("searchText")
        } verify {
            textIsSearched("searchText")
        }
    }

    @Test
    fun withoutASwipeUpEndReachedIsNotInvoked() {
        heroListRobot(composeTestRule) {
        } verify {
            endIsNotReached()
        }
    }

    @Test
    fun whenASwipeUpIsPerformedEndReachedIsInvoked() {
        heroListRobot(composeTestRule) {
            scrollBottom()
        } verify {
            endIsReached()
        }
    }
}
