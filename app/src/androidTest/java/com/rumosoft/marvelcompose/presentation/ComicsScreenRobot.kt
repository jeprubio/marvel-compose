package com.rumosoft.marvelcompose.presentation

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.core.app.launchActivity

fun comicsScreenRobot(composeTestRule: ComposeContentTestRule, func: ComicsScreenRobot.() -> Unit) =
    ComicsScreenRobot(composeTestRule).apply { func() }

@OptIn(ExperimentalTestApi::class)
class ComicsScreenRobot(private val composeTestRule: ComposeContentTestRule) {

    init {
        launchActivity<MainActivity>()
        composeTestRule.onNodeWithText("Comics").performClick()
        composeTestRule.waitUntilExactlyOneExists(hasText("Comics"))
    }

    infix fun verify(func: ComicsScreenResult.() -> Unit) =
        ComicsScreenResult(composeTestRule).apply { func() }
}

class ComicsScreenResult(
    private val composeTestRule: ComposeContentTestRule,
) {
    fun comicsAreDisplayed() {
        textIsDisplayed("Comics")
    }

    fun comicIsDisplayed(comicName: String) {
        textIsDisplayed(comicName)
    }

    @OptIn(ExperimentalTestApi::class)
    fun textIsDisplayed(text: String) {
        composeTestRule.waitUntilExactlyOneExists(hasText(text))
    }
}
