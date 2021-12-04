package com.rumosoft.comics.presentation.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import androidx.navigation.navigation
import com.rumosoft.comics.presentation.screen.ComicDetailsScreen
import com.rumosoft.comics.presentation.screen.ComicsScreen
import com.rumosoft.comics.presentation.viewmodel.ComicDetailsViewModel
import com.rumosoft.comics.presentation.viewmodel.ComicListViewModel
import com.rumosoft.commons.DeepLinks
import dev.jeziellago.compose.multinavcompose.NavComposableModule

@ExperimentalFoundationApi
@ExperimentalComposeUiApi
@ExperimentalAnimationApi
val comicsNavModule = NavComposableModule { graph, navController ->
    graph.navigation(startDestination = NavComicItem.Comics.route, route = "cmcs") {
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
            require(comicId != null) { "A comic should be selected" }
            val viewModel: ComicDetailsViewModel = hiltViewModel(navBackStackEntry)
            viewModel.fetchComicData(comicId)
            ComicDetailsScreen(
                viewModel = viewModel,
                onBackPressed = {
                    try {
                        val entry = navController.getBackStackEntry(NavComicItem.Comics.route)
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
}
