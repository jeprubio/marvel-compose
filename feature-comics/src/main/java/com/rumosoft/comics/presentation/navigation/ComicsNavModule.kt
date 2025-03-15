package com.rumosoft.comics.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import com.rumosoft.comics.presentation.screen.ComicDetailsScreenContent
import com.rumosoft.comics.presentation.screen.ComicDetailsTopBar
import com.rumosoft.comics.presentation.screen.ComicsScreenContent
import com.rumosoft.comics.presentation.screen.ComicsTopBar
import com.rumosoft.comics.presentation.viewmodel.ComicDetailsViewModel
import com.rumosoft.comics.presentation.viewmodel.ComicListViewModel
import com.rumosoft.components.presentation.deeplinks.ComicDetails
import com.rumosoft.components.presentation.deeplinks.ComicsScreen
import com.rumosoft.components.presentation.deeplinks.DEEP_LINKS_BASE_PATH

fun NavGraphBuilder.comicsGraph(
    navController: NavHostController,
    setTopBarContent: (@Composable () -> Unit) -> Unit,
) {
    composable<ComicsScreen>(
        deepLinks = listOf(navDeepLink<ComicsScreen>(basePath = "$DEEP_LINKS_BASE_PATH/comics")),
    ) { navBackStackEntry ->
        val viewModel: ComicListViewModel = hiltViewModel(navBackStackEntry)
        val comicsScreenState by viewModel.comicsListScreenState.collectAsStateWithLifecycle()
        LaunchedEffect(key1 = comicsScreenState) {
            comicsScreenState.selectedComic?.let { selectedComic ->
                viewModel.resetSelectedComic()
                navController.navigate(ComicDetails(selectedComic.id))
            }
        }
        var search by remember { mutableStateOf("") }
        val onToggleSearchClick = {
            viewModel.onToggleSearchClick()
        }
        val onValueChanged = { value: String ->
            search = value
            viewModel.onQueryChanged(value)
        }
        setTopBarContent {
            ComicsTopBar(
                search,
                comicsScreenState.showingSearchBar,
                onToggleSearchClick,
                onValueChanged
            )
        }
        ComicsScreenContent(
            comicListState = comicsScreenState.comicListState,
        )
    }
    composable<ComicDetails>(
        deepLinks = listOf(navDeepLink<ComicDetails>(basePath = "$DEEP_LINKS_BASE_PATH/comics")),
    ) { navBackStackEntry ->
        val viewModel: ComicDetailsViewModel = hiltViewModel(navBackStackEntry)
        val screenState by viewModel.detailsState.collectAsStateWithLifecycle()
        setTopBarContent {
            ComicDetailsTopBar(
                onBackPressed = {
                    val navigatedToComics = navController.popBackStack(
                        route = ComicsScreen,
                        inclusive = false,
                    )
                    if (!navigatedToComics) {
                        navController.navigate(ComicsScreen)
                    }
                }
            )
        }
        ComicDetailsScreenContent(
            screenState = screenState,
        )
    }
}
