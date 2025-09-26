package com.rumosoft.characters.presentation.screen

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import com.rumosoft.characters.R
import com.rumosoft.components.R as Rcomponents
import com.rumosoft.characters.infrastructure.sampleData.SampleData
import com.rumosoft.characters.presentation.screen.state.BuildUI
import com.rumosoft.characters.presentation.viewmodel.state.HeroListState
import com.rumosoft.components.presentation.component.SearchBar
import com.rumosoft.components.presentation.component.SectionTopBar
import com.rumosoft.components.presentation.theme.MarvelComposeTheme

@Composable
fun CharactersTopBar(
    searchText: String,
    showSearchBar: Boolean,
    onToggleSearchClick: () -> Unit,
    onValueChanged: (String) -> Unit
) {
    SearchableTitle(searchText, showSearchBar, onToggleSearchClick, onValueChanged)
}

@Composable
fun HeroListScreenContent(
    heroListState: HeroListState,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        ResultBox(heroListState)
    }
}

@Composable
private fun SearchableTitle(
    searchText: String,
    showSearchBar: Boolean,
    onToggleSearchClick: () -> Unit = {},
    onValueChanged: (String) -> Unit = {},
) {
    Column {
        SectionTopBar(
            sectionName = R.string.characters,
            icon = ImageVector.vectorResource(Rcomponents.drawable.ic_search),
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
    val heroes = SampleData.heroesSample
    MarvelComposeTheme {
        HeroListScreenContent(
            heroListState = HeroListState.Success(heroes),
        )
    }
}
