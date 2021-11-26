package com.rumosoft.comics.presentation.screen

import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.rumosoft.comics.R
import com.rumosoft.comics.infrastructure.sampleData.SampleData
import com.rumosoft.comics.presentation.viewmodel.ComicListViewModel
import com.rumosoft.comics.presentation.viewmodel.state.ComicListState
import com.rumosoft.components.presentation.component.SectionTabBar
import com.rumosoft.components.presentation.theme.MarvelComposeTheme

@ExperimentalFoundationApi
@Composable
fun ComicsScreen(viewModel: ComicListViewModel) {
    val comicsListScreenState by viewModel.comicsListScreenState.collectAsState()
    ComicsScreenContent(comicsListScreenState.comicListState)
}

@ExperimentalFoundationApi
@Composable
fun ComicsScreenContent(comicListState: ComicListState) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        SectionTabBar(R.string.comics)
        ResultBox(comicsListState = comicListState)
    }
}

@ExperimentalFoundationApi
@Composable
private fun ResultBox(comicsListState: ComicListState) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        comicsListState.BuildUI()
    }
}

@ExperimentalFoundationApi
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
            comicListState = ComicListState.Success(comics)
        )
    }
}
