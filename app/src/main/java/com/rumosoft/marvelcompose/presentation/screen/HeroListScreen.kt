package com.rumosoft.marvelcompose.presentation.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.rumosoft.marvelcompose.R
import com.rumosoft.marvelcompose.infrastructure.sampleData.SampleData
import com.rumosoft.marvelcompose.presentation.theme.MarvelComposeTheme
import com.rumosoft.marvelcompose.presentation.viewmodel.HeroListViewModel
import com.rumosoft.marvelcompose.presentation.viewmodel.state.HeroListState

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
    HeroListScreenContent(heroListState = heroListScreenState.heroListState)
}

@Composable
fun HeroListScreenContent(
    heroListState: HeroListState
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.img_marvel),
            contentDescription = stringResource(id = R.string.marvel_logo),
            alignment = Alignment.Center,
            modifier = Modifier
                .width(200.dp)
                .padding(16.dp),
        )
        ResultBox(heroListState)
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

@Preview(showBackground = true)
@Composable
fun HeroListScreenPreview() {
    val heroes = SampleData.heroesSample
    MarvelComposeTheme {
        HeroListScreenContent(HeroListState.Success(heroes))
    }
}
