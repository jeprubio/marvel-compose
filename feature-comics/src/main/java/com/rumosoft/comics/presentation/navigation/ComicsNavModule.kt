package com.rumosoft.comics.presentation.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import com.rumosoft.comics.presentation.screen.ComicDetailsScreen
import com.rumosoft.comics.presentation.screen.ComicsScreen
import com.rumosoft.comics.presentation.viewmodel.ComicDetailsViewModel
import com.rumosoft.comics.presentation.viewmodel.ComicListViewModel
import com.rumosoft.commons.DeepLinks

@ExperimentalFoundationApi
@ExperimentalComposeUiApi
@ExperimentalAnimationApi
fun NavGraphBuilder.comicsGraph(navController: NavHostController) {
    composable(
        NavComicItem.Comics.route,
        deepLinks = listOf(navDeepLink { uriPattern = NavComicItem.Comics.deepLink }),
    ) { navBackStackEntry ->
        val viewModel: ComicListViewModel = hiltViewModel(navBackStackEntry)
        ComicsScreen(
            viewModel = viewModel,
            onComicSelected = { selectedComic ->
                viewModel.resetSelectedComic()
                navController.currentBackStackEntry?.arguments?.putInt(
                    "comicId",
                    selectedComic.id
                )
                navController.navigate(NavComicItem.Details.route)
            }
        )
    }
    composable(
        NavComicItem.Details.route,
        deepLinks = listOf(navDeepLink { uriPattern = NavComicItem.Details.deepLink }),
    ) { navBackStackEntry ->
        val comicId: Int? =
            navController.previousBackStackEntry?.arguments?.getInt("comicId")
        val viewModel: ComicDetailsViewModel = hiltViewModel(navBackStackEntry)
        comicId?.let {
            viewModel.fetchComicData(comicId)
        }
        ComicDetailsScreen(
            viewModel = viewModel,
            onBackPressed = {
                try {
                    navController.getBackStackEntry(NavComicItem.Comics.route)
                    navController.popBackStack(
                        route = NavComicItem.Comics.route,
                        inclusive = false
                    )
                } catch (e: Exception) {
                    navController.navigate(DeepLinks.Comics.route.toUri())
                }
            }
        )
    }
}
