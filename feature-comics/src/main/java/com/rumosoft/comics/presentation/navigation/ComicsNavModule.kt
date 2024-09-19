package com.rumosoft.comics.presentation.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import com.rumosoft.comics.presentation.screen.ComicDetailsScreen
import com.rumosoft.comics.presentation.screen.ComicsScreen
import com.rumosoft.comics.presentation.viewmodel.ComicDetailsViewModel
import com.rumosoft.comics.presentation.viewmodel.ComicListViewModel
import com.rumosoft.components.presentation.deeplinks.ComicDetails
import com.rumosoft.components.presentation.deeplinks.ComicsScreen
import com.rumosoft.components.presentation.deeplinks.DEEP_LINKS_BASE_PATH

fun NavGraphBuilder.comicsGraph(navController: NavHostController) {
    composable<ComicsScreen>(
        deepLinks = listOf(navDeepLink<ComicsScreen>(basePath = "$DEEP_LINKS_BASE_PATH/comics")),
    ) { navBackStackEntry ->
        val viewModel: ComicListViewModel = hiltViewModel(navBackStackEntry)
        ComicsScreen(
            viewModel = viewModel,
            onComicSelected = { selectedComic ->
                viewModel.resetSelectedComic()
                navController.navigate(ComicDetails(selectedComic.id))
            },
        )
    }
    composable<ComicDetails>(
        deepLinks = listOf(navDeepLink<ComicDetails>(basePath = "$DEEP_LINKS_BASE_PATH/comics")),
    ) { navBackStackEntry ->
        val viewModel: ComicDetailsViewModel = hiltViewModel(navBackStackEntry)
        ComicDetailsScreen(
            viewModel = viewModel,
            onBackPressed = {
                val navigatedToComics = navController.popBackStack(
                    route = ComicsScreen,
                    inclusive = false,
                )
                if (!navigatedToComics) {
                    navController.navigate(ComicsScreen)
                }
            },
        )
    }
}
