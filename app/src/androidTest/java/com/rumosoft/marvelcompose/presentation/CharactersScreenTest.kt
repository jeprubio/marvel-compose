package com.rumosoft.marvelcompose.presentation

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class CharactersScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Test
    fun checkCharactersScreenShowsCharactersTitle() {
        charactersScreenRobot {
        } verify {
            charactersAreDisplayed()
        }
    }

    @Test
    fun checkCharactersScreenShowsCharacters() {
        charactersScreenRobot {
        } verify {
            characterIsDisplayed("character 1")
        }
    }

    @Test
    fun checkCharactersScreenLoadsMoreResultsOnScroll() {
        charactersScreenRobot {
            scrollBottom()
        } verify {
            moreCharactersLoaded()
        }
    }

    @Test
    fun checkCharactersDetailsAreDisplayedAfterOneIsSelected() {
        charactersScreenRobot {
            clickOnCharacter("character 1")
        } verify {
            characterDetailsAreDisplayed("character 1")
        }
    }
}
