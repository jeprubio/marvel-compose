package com.rumosoft.comics.presentation.navigation

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.ui.LocalNavAnimatedContentScope
import com.rumosoft.comics.presentation.screen.ComicDetailsScreenContent
import com.rumosoft.comics.presentation.screen.ComicDetailsTopBar
import com.rumosoft.comics.presentation.screen.ComicsScreenContent
import com.rumosoft.comics.presentation.screen.ComicsTopBar
import com.rumosoft.comics.presentation.viewmodel.ComicDetailsViewModel
import com.rumosoft.comics.presentation.viewmodel.ComicListViewModel
import com.rumosoft.components.presentation.component.LocalSharedElementVisibilityScope
import com.rumosoft.components.presentation.deeplinks.ComicDetails
import com.rumosoft.components.presentation.deeplinks.ComicsScreen

fun EntryProviderScope<NavKey>.comicsGraph(
    navigate: (NavKey) -> Unit,
    goBack: () -> Unit,
    setTopBarContent: (@androidx.compose.runtime.Composable () -> Unit) -> Unit,
) {
    entry<ComicsScreen> {
        val animatedScope = LocalNavAnimatedContentScope.current
        val viewModel: ComicListViewModel = hiltViewModel()
        val comicsScreenState by viewModel.comicsListScreenState.collectAsStateWithLifecycle()
        LaunchedEffect(key1 = comicsScreenState) {
            comicsScreenState.selectedComic?.let { selectedComic ->
                viewModel.resetSelectedComic()
                navigate(ComicDetails(selectedComic.id))
            }
        }
        setTopBarContent {
            ComicsTopBar()
        }
        CompositionLocalProvider(LocalSharedElementVisibilityScope provides animatedScope) {
            ComicsScreenContent(
                comicListState = comicsScreenState.comicListState,
                onComicClick = viewModel::comicClicked,
                onEndReached = viewModel::onReachedEnd,
                onRetry = viewModel::retry,
            )
        }
    }
    entry<ComicDetails> { key ->
        val animatedScope = LocalNavAnimatedContentScope.current
        val viewModel: ComicDetailsViewModel = hiltViewModel()
        viewModel.initialize(key.comicId)
        val screenState by viewModel.detailsState.collectAsStateWithLifecycle()
        setTopBarContent {
            ComicDetailsTopBar(
                onBackPressed = { goBack() }
            )
        }
        CompositionLocalProvider(LocalSharedElementVisibilityScope provides animatedScope) {
            ComicDetailsScreenContent(
                screenState = screenState,
                onRetry = viewModel::retry,
            )
        }
    }
}
