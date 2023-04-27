package com.rumosoft.comics.presentation.screen

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
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
import com.rumosoft.comics.R
import com.rumosoft.comics.infrastructure.sampleData.SampleData
import com.rumosoft.comics.presentation.screen.state.BuildUI
import com.rumosoft.comics.presentation.viewmodel.ComicListViewModel
import com.rumosoft.comics.presentation.viewmodel.state.ComicListState
import com.rumosoft.commons.domain.model.Comic
import com.rumosoft.components.presentation.component.SearchBar
import com.rumosoft.components.presentation.component.SectionTopBar
import com.rumosoft.components.presentation.theme.MarvelComposeTheme

@Composable
fun ComicsScreen(
    viewModel: ComicListViewModel,
    onComicSelected: (comic: Comic) -> Unit = {},
) {
    val comicsListScreenState by viewModel.comicsListScreenState.collectAsStateWithLifecycle()
    LaunchedEffect(key1 = comicsListScreenState) {
        comicsListScreenState.selectedComic?.let {
            onComicSelected(it)
        }
    }
    ComicsScreenContent(
        comicListState = comicsListScreenState.comicListState,
        showSearchBar = comicsListScreenState.showingSearchBar,
        onToggleSearchClick = viewModel::onToggleSearchClick,
        searchText = remember {
            mutableStateOf(TextFieldValue(comicsListScreenState.textSearched))
        },
        onValueChanged = viewModel::onQueryChanged,
    )
}

@Composable
fun ComicsScreenContent(
    comicListState: ComicListState,
    showSearchBar: Boolean,
    searchText: MutableState<TextFieldValue>,
    onToggleSearchClick: () -> Unit = {},
    onValueChanged: (String) -> Unit = {},
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        SearchableTitle(
            searchText = searchText,
            showSearchBar = showSearchBar,
            onValueChanged = onValueChanged,
            onToggleSearchClick = onToggleSearchClick,
        )
        ResultBox(comicsListState = comicListState)
    }
}

@Composable
private fun SearchableTitle(
    searchText: MutableState<TextFieldValue>,
    showSearchBar: Boolean,
    onValueChanged: (String) -> Unit,
    onToggleSearchClick: () -> Unit,
) {
    SectionTopBar(
        sectionName = R.string.comics,
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
                onValueChanged = onValueChanged,
                onLeadingClicked = onToggleSearchClick,
            )
        }
    }
}

@Composable
private fun ResultBox(comicsListState: ComicListState) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        comicsListState.BuildUI()
    }
}

@Preview(showBackground = true)
@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
fun ComicsScreenPreview() {
    val searchState = remember { mutableStateOf(TextFieldValue("")) }
    val comics = SampleData.comicsSample
    MarvelComposeTheme {
        ComicsScreenContent(
            comicListState = ComicListState.Success(comics),
            showSearchBar = false,
            searchText = searchState,
        )
    }
}
