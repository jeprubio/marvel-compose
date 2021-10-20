package com.rumosoft.marvelcompose.presentation.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumosoft.marvelcompose.infrastructure.sampleData.SampleData
import com.rumosoft.marvelcompose.presentation.theme.MarvelComposeTheme
import com.rumosoft.marvelcompose.presentation.viewmodel.HeroListViewModel
import com.rumosoft.marvelcompose.presentation.viewmodel.state.HeroListResult

@Composable
fun HeroListScreen(viewModel: HeroListViewModel) {
    val heroListScreenState by viewModel.heroListScreenState.collectAsState()
    HeroListScreenContent(heroListResult = heroListScreenState.heroListResult)
}

@Composable
fun HeroListScreenContent(
    heroListResult: HeroListResult
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        ResultBox(heroListResult)
    }
}

@Composable
private fun ResultBox(heroListResult: HeroListResult) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        heroListResult.BuildUI()
    }
}

@Preview(showBackground = true)
@Composable
fun HeroListScreenPreview() {
    val heroes = SampleData.heroesSample
    MarvelComposeTheme {
        HeroListScreenContent(HeroListResult.Success(heroes))
    }
}
