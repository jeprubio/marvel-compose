package com.rumosoft.characters.presentation.screen

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsIgnoringVisibility
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rumosoft.characters.R
import com.rumosoft.characters.domain.model.Character
import com.rumosoft.characters.infrastructure.sampleData.SampleData
import com.rumosoft.characters.presentation.screen.state.BuildUI
import com.rumosoft.characters.presentation.viewmodel.HeroListViewModel
import com.rumosoft.characters.presentation.viewmodel.state.HeroListState
import com.rumosoft.components.presentation.component.SearchBar
import com.rumosoft.components.presentation.component.SectionTopBar
import com.rumosoft.components.presentation.theme.MarvelComposeTheme

@Composable
fun HeroListScreen(
    viewModel: HeroListViewModel,
    onCharacterSelected: (character: Character) -> Unit = {},
) {
    val heroListScreenState by viewModel.heroListScreenState.collectAsStateWithLifecycle()
    LaunchedEffect(key1 = heroListScreenState) {
        heroListScreenState.selectedCharacter?.let {
            onCharacterSelected(it)
        }
    }
    HeroListScreenContent(
        heroListState = heroListScreenState.heroListState,
        showSearchBar = heroListScreenState.showingSearchBar,
        onToggleSearchClick = viewModel::onToggleSearchClick,
        searchText = remember {
            mutableStateOf(TextFieldValue(heroListScreenState.textSearched))
        },
        onValueChanged = viewModel::onQueryChanged,
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun HeroListScreenContent(
    heroListState: HeroListState,
    showSearchBar: Boolean,
    searchText: MutableState<TextFieldValue>,
    onToggleSearchClick: () -> Unit = {},
    onValueChanged: (String) -> Unit = {},
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Scaffold(
            contentWindowInsets = WindowInsets.systemBarsIgnoringVisibility,
            topBar =  {
                SearchableTitle(searchText, showSearchBar, onToggleSearchClick, onValueChanged)
            }
        ) { contentPadding ->
            ResultBox(heroListState, modifier = Modifier.padding(contentPadding))
        }
    }
}

@Composable
private fun SearchableTitle(
    searchText: MutableState<TextFieldValue>,
    showSearchBar: Boolean,
    onToggleSearchClick: () -> Unit = {},
    onValueChanged: (String) -> Unit = {},
) {
    Column {
        SectionTopBar(
            sectionName = R.string.characters,
            icon = Icons.Default.Search,
            onIconClick = onToggleSearchClick,
        )
        AnimatedVisibility(
            showSearchBar,
            enter = fadeIn(),
            exit = slideOutVertically() + fadeOut(),
        ) {
            if (showSearchBar) {
                SearchBar(
                    state = searchText,
                    hint = stringResource(id = com.rumosoft.components.R.string.search_hint),
                    requestFocus = true,
                    onValueChanged = onValueChanged,
                    onLeadingClicked = onToggleSearchClick,
                )
            }
        }
    }
}

@Composable
private fun ResultBox(
    heroListState: HeroListState,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxSize(),
    ) {
        heroListState.BuildUI()
    }
}

@Preview(showBackground = true)
@Preview(
    showBackground = true,
    uiMode = UI_MODE_NIGHT_YES,
)
@Composable
fun HeroListScreenPreview() {
    val searchState = remember { mutableStateOf(TextFieldValue("")) }
    val heroes = SampleData.heroesSample
    MarvelComposeTheme {
        HeroListScreenContent(
            heroListState = HeroListState.Success(heroes),
            showSearchBar = false,
            searchText = searchState,
        )
    }
}
