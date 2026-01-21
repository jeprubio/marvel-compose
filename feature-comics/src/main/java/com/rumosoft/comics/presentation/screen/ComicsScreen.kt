package com.rumosoft.comics.presentation.screen

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.rumosoft.comics.R
import com.rumosoft.comics.domain.model.Comic
import com.rumosoft.comics.infrastructure.sampleData.SampleData
import com.rumosoft.comics.presentation.screen.state.BuildUI
import com.rumosoft.comics.presentation.viewmodel.state.ComicListState
import com.rumosoft.components.presentation.component.SectionTopBar
import com.rumosoft.components.presentation.theme.MarvelComposeTheme

@Composable
fun ComicsTopBar() {
    SectionTopBar(sectionName = R.string.comics)
}

@Composable
fun ComicsScreenContent(
    comicListState: ComicListState,
    onComicClick: (Comic) -> Unit = {},
    onEndReached: () -> Unit = {},
    onRetry: () -> Unit = {},
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        ResultBox(
            comicsListState = comicListState,
            onComicClick = onComicClick,
            onEndReached = onEndReached,
            onRetry = onRetry,
        )
    }
}


@Composable
private fun ResultBox(
    comicsListState: ComicListState,
    onComicClick: (Comic) -> Unit,
    onEndReached: () -> Unit,
    onRetry: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        comicsListState.BuildUI(
            onComicClick = onComicClick,
            onEndReached = onEndReached,
            onRetry = onRetry,
        )
    }
}

@Preview(showBackground = true)
@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
fun ComicsScreenPreview() {
    val comics = SampleData.comicsSample
    MarvelComposeTheme {
        ComicsScreenContent(
            comicListState = ComicListState.Success(comics),
        )
    }
}
