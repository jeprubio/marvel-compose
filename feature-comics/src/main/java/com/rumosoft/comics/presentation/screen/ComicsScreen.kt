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
import com.rumosoft.comics.domain.model.Comic
import com.rumosoft.comics.infrastructure.sampleData.SampleData
import com.rumosoft.comics.presentation.screen.state.BuildUI
import com.rumosoft.comics.presentation.viewmodel.ComicListViewModel
import com.rumosoft.comics.presentation.viewmodel.state.ComicListState
import com.rumosoft.components.presentation.component.SearchBar
import com.rumosoft.components.presentation.component.SectionTopBar
import com.rumosoft.components.presentation.theme.MarvelComposeTheme

@Composable
fun ComicsTopBar(
    searchText: String,
    showSearchBar: Boolean,
    onToggleSearchClick: () -> Unit,
    onValueChanged: (String) -> Unit
) {
    SearchableTitle(searchText, showSearchBar, onToggleSearchClick, onValueChanged)
}

@Composable
fun ComicsScreenContent(
    comicListState: ComicListState,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        ResultBox(comicsListState = comicListState)
    }
}

@Composable
private fun SearchableTitle(
    searchText: String,
    showSearchBar: Boolean,
    onToggleSearchClick: () -> Unit,
    onValueChanged: (String) -> Unit,
) {
    Column {
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
                    search = searchText,
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
        )
    }
}
