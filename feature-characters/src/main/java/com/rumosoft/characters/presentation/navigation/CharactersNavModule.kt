package com.rumosoft.characters.presentation.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.rumosoft.characters.presentation.screen.CharacterDetailsTopBar
import com.rumosoft.characters.presentation.screen.CharactersTopBar
import com.rumosoft.characters.presentation.screen.DetailsScreenContent
import com.rumosoft.characters.presentation.screen.HeroListScreenContent
import com.rumosoft.characters.presentation.viewmodel.DetailsViewModel
import com.rumosoft.characters.presentation.viewmodel.HeroListViewModel
import com.rumosoft.components.presentation.deeplinks.CharacterDetails
import com.rumosoft.components.presentation.deeplinks.CharactersScreen

fun EntryProviderScope<NavKey>.charactersGraph(
    navigate: (NavKey) -> Unit,
    onComicSelected: (Int) -> Unit,
    goBack: () -> Unit,
    setTopBarContent: (@androidx.compose.runtime.Composable () -> Unit) -> Unit,
) {
    entry<CharactersScreen> {
        val viewModel: HeroListViewModel = hiltViewModel()
        val heroListScreenState by viewModel.heroListScreenState.collectAsStateWithLifecycle()
        LaunchedEffect(key1 = heroListScreenState) {
            heroListScreenState.selectedCharacter?.let { selectedCharacter ->
                viewModel.resetSelectedCharacter()
                navigate(CharacterDetails(selectedCharacter.id))
            }
        }
        setTopBarContent {
            CharactersTopBar()
        }
        HeroListScreenContent(
            heroListState = heroListScreenState.heroListState,
            onCharacterClick = viewModel::characterClicked,
            onEndReached = viewModel::onReachedEnd,
            onRetry = viewModel::retry,
        )
    }
    entry<CharacterDetails> { key ->
        val viewModel: DetailsViewModel = hiltViewModel()
        viewModel.initialize(key.characterId)
        val screenState by viewModel.detailsState.collectAsStateWithLifecycle()
        setTopBarContent {
            CharacterDetailsTopBar(
                onBackPressed = { goBack() }
            )
        }
        DetailsScreenContent(
            screenState,
            onComicSelected = { comicId ->
                onComicSelected(comicId)
            },
            onRetry = viewModel::retry,
        )
    }
}
