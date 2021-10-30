package com.rumosoft.marvelcompose.presentation.screen

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Text
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.rumosoft.marvelcompose.R
import com.rumosoft.marvelcompose.infrastructure.sampleData.SampleData
import com.rumosoft.marvelcompose.presentation.component.SearchBar
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
    HeroListTitle(onToggleSearchClick)
    AnimatedVisibility(
        showSearchBar,
        enter = fadeIn(),
        exit = slideOutVertically() + fadeOut()
    ) {
        if (showSearchBar) SearchBar(searchText, onValueChanged)
    }
}

@Composable
private fun HeroListTitle(
    onToggleSearchClick: () -> Unit = {}
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .background(MarvelComposeTheme.colors.surface)
            .padding(MarvelComposeTheme.paddings.medium),
    ) {
        val marvelFont = Font(R.font.marvel_regular)
        Text(
            text = stringResource(id = R.string.characters).uppercase(),
            color = Color.White,
            fontSize = 70.sp,
            fontFamily = FontFamily(marvelFont),
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(MarvelComposeTheme.paddings.defaultPadding),
        )
        Icon(
            imageVector = Icons.Default.Search,
            tint = Color.White,
            contentDescription = stringResource(id = R.string.search),
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .clickable { onToggleSearchClick() }
                .padding(MarvelComposeTheme.paddings.medium),
        )
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
