package com.rumosoft.comics.screen

import androidx.compose.ui.test.junit4.createComposeRule
import org.junit.Rule
import org.junit.Test

class ComicsScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun whenScreenIsLoadedItShouldShowTheListOfComics() {
        comicsScreenRobot {
        } verify {
            comicIsDisplayed("comic 1")
        }
    }

    @Test
    fun whenMagnifierTappedItShowsSearchTextInput() {
        comicsScreenRobot {
            onMagnifierTapped()
        } verify {
            searchIsDisplayed()
        }
    }

    @Test
    fun whenComicIsSearchedItsShownInSearchTextInput() {
        comicsScreenRobot {
            onComicSearched("searchText")
        } verify {
            textIsSearched("searchText")
        }
    }

    @Test
    fun withoutASwipeUpEndReachedIsNotInvoked() {
        comicsScreenRobot {
        } verify {
            endIsNotReached()
        }
    }

    @Test
    fun whenASwipeUpIsPerformedEndReachedIsInvoked() {
        comicsScreenRobot {
            scrollBottom()
        } verify {
            endIsReached()
        }
    }
}
