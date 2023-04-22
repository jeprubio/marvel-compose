package com.rumosoft.characters.presentation.navigation

import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import com.rumosoft.characters.presentation.screen.DetailsScreen
import com.rumosoft.characters.presentation.screen.HeroListScreen
import com.rumosoft.characters.presentation.viewmodel.DetailsViewModel
import com.rumosoft.characters.presentation.viewmodel.HeroListViewModel
import com.rumosoft.commons.DeepLinks

fun NavGraphBuilder.charactersGraph(navController: NavHostController) {
    composable(
        route = NavCharItem.Characters.route,
        deepLinks = listOf(navDeepLink { uriPattern = NavCharItem.Characters.deepLink }),
    ) { navBackStackEntry ->
        val viewModel: HeroListViewModel = hiltViewModel(navBackStackEntry)
        HeroListScreen(
            viewModel = viewModel,
            onCharacterSelected = { selectedCharacter ->
                viewModel.resetSelectedCharacter()
                navController.navigate(NavCharItem.Details.routeOfCharacter(selectedCharacter))
            },
        )
    }
    composable(
        route = NavCharItem.Details.route,
        arguments = NavCharItem.Details.navArgs,
    ) { navBackStackEntry ->
        val viewModel: DetailsViewModel = hiltViewModel(navBackStackEntry)
        DetailsScreen(
            viewModel = viewModel,
            onBackPressed = {
                navController.popBackStack(
                    route = NavCharItem.Characters.route,
                    inclusive = false,
                )
            },
            onComicSelected = { comicId ->
                navController.navigate(DeepLinks.ComicDetails.routeOfComic(comicId).toUri())
            },
        )
    }
}
