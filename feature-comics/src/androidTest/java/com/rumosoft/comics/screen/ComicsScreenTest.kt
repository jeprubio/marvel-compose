package com.rumosoft.comics.screen

import androidx.compose.ui.test.junit4.createComposeRule
import org.junit.Rule
import org.junit.Test

class ComicsScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun whenScreenIsLoadedItShouldShowTheListOfComics() {
        comicsScreenRobot(composeTestRule) {
        } verify {
            comicIsDisplayed("comic 1")
        }
    }

    @Test
    fun whenMagnifierTappedItShowsSearchTextInput() {
        comicsScreenRobot(composeTestRule) {
            onMagnifierTapped()
        } verify {
            searchIsDisplayed()
        }
    }

    @Test
    fun whenComicIsSearchedItsShownInSearchTextInput() {
        comicsScreenRobot(composeTestRule) {
            onComicSearched("searchText")
        } verify {
            textIsSearched("searchText")
        }
    }

    @Test
    fun withoutASwipeUpEndReachedIsNotInvoked() {
        comicsScreenRobot(composeTestRule) {
        } verify {
            endIsNotReached()
        }
    }

    @Test
    fun whenASwipeUpIsPerformedEndReachedIsInvoked() {
        comicsScreenRobot(composeTestRule) {
            scrollBottom()
        } verify {
            endIsReached()
        }
    }
}
