package com.rumosoft.characters.presentation.navigation

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
import com.rumosoft.characters.presentation.screen.CharacterDetailsTopBar
import com.rumosoft.characters.presentation.screen.CharactersTopBar
import com.rumosoft.characters.presentation.screen.DetailsScreenContent
import com.rumosoft.characters.presentation.screen.HeroListScreenContent
import com.rumosoft.characters.presentation.viewmodel.DetailsViewModel
import com.rumosoft.characters.presentation.viewmodel.HeroListViewModel
import com.rumosoft.components.presentation.deeplinks.CharacterDetails
import com.rumosoft.components.presentation.deeplinks.CharactersScreen
import com.rumosoft.components.presentation.deeplinks.ComicDetails
import com.rumosoft.components.presentation.deeplinks.DEEP_LINKS_BASE_PATH

fun NavGraphBuilder.charactersGraph(
    navController: NavHostController,
    setTopBarContent: (@Composable () -> Unit) -> Unit,
) {
    composable<CharactersScreen>(
        deepLinks = listOf(navDeepLink<CharactersScreen>(basePath = "$DEEP_LINKS_BASE_PATH/characters")),
    ) { navBackStackEntry ->
        val viewModel: HeroListViewModel = hiltViewModel(navBackStackEntry)
        val heroListScreenState by viewModel.heroListScreenState.collectAsStateWithLifecycle()
        LaunchedEffect(key1 = heroListScreenState) {
            heroListScreenState.selectedCharacter?.let { selectedCharacter ->
                viewModel.resetSelectedCharacter()
                navController.navigate(CharacterDetails(selectedCharacter.id))
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
            CharactersTopBar(
                search,
                heroListScreenState.showingSearchBar,
                onToggleSearchClick,
                onValueChanged,
            )
        }
        HeroListScreenContent(
            heroListState = heroListScreenState.heroListState,
        )
    }
    composable<CharacterDetails>(
        deepLinks = listOf(navDeepLink<CharacterDetails>(basePath = "$DEEP_LINKS_BASE_PATH/characters")),
    ) { navBackStackEntry ->
        val viewModel: DetailsViewModel = hiltViewModel(navBackStackEntry)
        val screenState by viewModel.detailsState.collectAsStateWithLifecycle()
        setTopBarContent {
            CharacterDetailsTopBar(
                onBackPressed = {
                    navController.popBackStack(
                        route = CharactersScreen,
                        inclusive = false,
                    )
                }
            )
        }
        DetailsScreenContent(
            screenState,
            onComicSelected = { comicId ->
                navController.navigate(ComicDetails(comicId))
            },
        )
    }
}
