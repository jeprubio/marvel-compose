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
        comicsScreenRobot {
        } verify {
            comicsAreDisplayed()
        }
    }

    @Test
    fun checkComicsScreenShowsComics() {
        comicsScreenRobot {
        } verify {
            comicIsDisplayed("comic 1")
        }
    }
}
