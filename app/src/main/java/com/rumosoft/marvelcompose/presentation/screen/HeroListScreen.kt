package com.rumosoft.marvelcompose.presentation.screen

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.rumosoft.marvelcompose.R
import com.rumosoft.marvelcompose.infrastructure.sampleData.SampleData
import com.rumosoft.marvelcompose.presentation.component.SearchBar
import com.rumosoft.marvelcompose.presentation.component.SectionTabBar
import com.rumosoft.marvelcompose.presentation.navigation.Screen
import com.rumosoft.marvelcompose.presentation.theme.MarvelComposeTheme
import com.rumosoft.marvelcompose.presentation.viewmodel.HeroListViewModel
import com.rumosoft.marvelcompose.presentation.viewmodel.state.HeroListState

@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@Composable
fun HeroListScreen(navController: NavController, viewModel: HeroListViewModel) {
    val heroListScreenState by viewModel.heroListScreenState.collectAsState()
    if (heroListScreenState.selectedHero != null) {
        viewModel.resetSelectedHero()
        navController.currentBackStackEntry?.arguments?.putParcelable(
            "hero",
            heroListScreenState.selectedHero
        )
        navController.navigate(Screen.HeroDetails.route)
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

@ExperimentalAnimationApi
@ExperimentalComposeUiApi
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
        SearchableTitle(searchText, showSearchBar, onToggleSearchClick, onValueChanged)
        Spacer(modifier = Modifier.padding(MarvelComposeTheme.paddings.medium))
        ResultBox(heroListState)
    }
}

@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@Composable
private fun SearchableTitle(
    searchText: MutableState<TextFieldValue>,
    showSearchBar: Boolean,
    onToggleSearchClick: () -> Unit = {},
    onValueChanged: (String) -> Unit = {},
) {
    SectionTabBar(R.string.characters, Icons.Default.Search, onToggleSearchClick)
    AnimatedVisibility(
        showSearchBar,
        enter = fadeIn(),
        exit = slideOutVertically() + fadeOut()
    ) {
        if (showSearchBar) SearchBar(searchText, onValueChanged)
    }
}

@Composable
private fun ResultBox(heroListState: HeroListState) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                start = 24.dp,
                end = 24.dp,
            )
    ) {
        heroListState.BuildUI()
    }
}

@ExperimentalAnimationApi
@ExperimentalComposeUiApi
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
        HeroListScreenContent(HeroListState.Success(heroes), false, searchState)
    }
}
