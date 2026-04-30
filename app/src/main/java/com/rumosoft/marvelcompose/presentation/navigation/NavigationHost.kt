package com.rumosoft.marvelcompose.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.rumosoft.characters.presentation.navigation.charactersGraph
import com.rumosoft.comics.presentation.navigation.comicsGraph

import com.rumosoft.components.presentation.deeplinks.ComicDetails
import com.rumosoft.components.presentation.deeplinks.ComicsScreen

const val NAVIGATION_HOST_TEST_TAG = "NavigationHost"

@Composable
fun NavigationHost(
    navigationState: NavigationState,
    navigator: Navigator,
    setTopBarContent: (@Composable () -> Unit) -> Unit,
    modifier: Modifier = Modifier,
) {
    val entryProvider = entryProvider<NavKey> {
        charactersGraph(
            navigate = navigator::navigate,
            onComicSelected = { comicId ->
                navigator.goBack()
                navigator.navigateOnTab(ComicsScreen, ComicDetails(comicId))
            },
            goBack = navigator::goBack,
            setTopBarContent = setTopBarContent,
        )
        comicsGraph(
            navigate = navigator::navigate,
            goBack = navigator::goBack,
            setTopBarContent = setTopBarContent,
        )
    }

    NavDisplay(
        entries = navigationState.toEntries(entryProvider),
        onBack = { navigator.goBack() },
        modifier = modifier.testTag(NAVIGATION_HOST_TEST_TAG),
    )
}
