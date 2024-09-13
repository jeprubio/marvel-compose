package com.rumosoft.characters.presentation.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import androidx.navigation.toRoute
import com.rumosoft.characters.presentation.screen.DetailsScreen
import com.rumosoft.characters.presentation.screen.HeroListScreen
import com.rumosoft.characters.presentation.viewmodel.DetailsViewModel
import com.rumosoft.characters.presentation.viewmodel.HeroListViewModel
import com.rumosoft.components.presentation.deeplinks.CharacterDetails
import com.rumosoft.components.presentation.deeplinks.CharactersScreen
import com.rumosoft.components.presentation.deeplinks.ComicDetails
import com.rumosoft.components.presentation.deeplinks.DEEP_LINKS_BASE_PATH

fun NavGraphBuilder.charactersGraph(navController: NavHostController) {
    composable<CharactersScreen>(
        deepLinks = listOf(navDeepLink<CharactersScreen>(basePath = "$DEEP_LINKS_BASE_PATH/characters")),
    ) { navBackStackEntry ->
        val viewModel: HeroListViewModel = hiltViewModel(navBackStackEntry)
        HeroListScreen(
            viewModel = viewModel,
            onCharacterSelected = { selectedCharacter ->
                viewModel.resetSelectedCharacter()
                navController.navigate(CharacterDetails(selectedCharacter.id))
            },
        )
    }
    composable<CharacterDetails>(
        deepLinks = listOf(navDeepLink<CharacterDetails>(basePath = "$DEEP_LINKS_BASE_PATH/characters")),
    ) { navBackStackEntry ->
        val viewModel: DetailsViewModel = hiltViewModel()
        LaunchedEffect(Unit) {
            val characterId = navBackStackEntry.toRoute<CharacterDetails>().characterId
            viewModel.setCharacter(characterId)
        }
        DetailsScreen(
            viewModel = viewModel,
            onBackPressed = {
                navController.popBackStack(
                    route = CharactersScreen,
                    inclusive = false,
                )
            },
            onComicSelected = { comicId ->
                navController.navigate(ComicDetails(comicId))
            },
        )
    }
}
