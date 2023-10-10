package com.rumosoft.marvelcompose.presentation

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.filters.LargeTest
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test

@LargeTest
@HiltAndroidTest
class MainActivityTest {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 2)
    var composeTestRule = createAndroidComposeRule<MainActivity>()

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun checkFirstElements() {
        composeTestRule.waitUntilExactlyOneExists(
            matcher = hasText("character 0"),
            timeoutMillis = 4_000
        )

        composeTestRule.onNodeWithText("character 0").assertIsDisplayed()
        composeTestRule.onNodeWithText("character 1").assertIsDisplayed()
        composeTestRule.onNodeWithText("character 2").assertIsDisplayed()
    }
}
