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
class ComicsScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Test
    fun checkComicsScreenShowsComicsTitle() {
        comicsScreenRobot(composeTestRule) {
        } verify {
            comicsAreDisplayed()
        }
    }

    @Test
    fun checkComicsScreenShowsComics() {
        comicsScreenRobot(composeTestRule) {
        } verify {
            comicIsDisplayed("comic 1")
            Thread.sleep(5000)
        }
    }
}