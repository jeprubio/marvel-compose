package com.rumosoft.comics.screen

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.platform.app.InstrumentationRegistry
import com.rumosoft.comics.presentation.screen.ComicDetailsScreenContent
import com.rumosoft.comics.presentation.screen.ComicDetailsTopBar
import com.rumosoft.comics.presentation.viewmodel.state.ComicDetailsState
import com.rumosoft.components.R
import com.rumosoft.components.presentation.theme.MarvelComposeTheme
import org.junit.Assert.assertTrue

fun ComicDetailsScreenTest.comicDetailsScreenRobot(func: ComicDetailsScreenRobot.() -> Unit) =
    ComicDetailsScreenRobot(composeTestRule).apply { func() }

class ComicDetailsScreenRobot(private val composeTestRule: ComposeContentTestRule) {
    private var backPressed = false

    init {
        composeTestRule.setContent {
            MarvelComposeTheme {
                Scaffold(
                    topBar = {
                        ComicDetailsTopBar(
                            onBackPressed = { backPressed = true },
                        )
                    },
                ) { innerPadding ->
                    Box(
                        modifier = Modifier.padding(innerPadding),
                    ) {
                        ComicDetailsScreenContent(
                            screenState = ComicDetailsState.Success(
                                comic = FakeComics.getSampleComics(1).first(),
                            ),
                        )
                    }
                }
            }
        }
    }

    fun onBackPressed() {
        composeTestRule.onNodeWithContentDescription(getString(R.string.go_back)).performClick()
    }

    infix fun verify(func: DetailsScreenResult.() -> Unit) =
        DetailsScreenResult(composeTestRule, backPressed).apply { func() }
}

class DetailsScreenResult(
    private val composeTestRule: ComposeContentTestRule,
    private val backPressed: Boolean,
) {
    fun comicIsDisplayed(name: String) {
        textIsDisplayed(name)
    }

    fun backWasPressed() {
        assertTrue(backPressed)
    }

    @OptIn(ExperimentalTestApi::class)
    fun textIsDisplayed(text: String) {
        composeTestRule.waitUntilExactlyOneExists(hasText(text))
        composeTestRule.onNodeWithText(text).assertIsDisplayed()
    }
}

private fun getString(@StringRes stringResource: Int) =
    InstrumentationRegistry.getInstrumentation().targetContext.getString(stringResource)
