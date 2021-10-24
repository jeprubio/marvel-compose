package com.rumosoft.marvelcompose.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
        HeroListTitle()
        ResultBox(heroListState)
    }
}

@Composable
private fun HeroListTitle() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = MarvelComposeTheme.paddings.defaultPadding)
            .background(MarvelComposeTheme.colors.surface),
    ) {
        val marvelFont = Font(R.font.marvel_regular)
        Text(
            text = stringResource(id = R.string.heroes).uppercase(),
            color = Color.White,
            fontSize = 70.sp,
            fontFamily = FontFamily(marvelFont),
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(MarvelComposeTheme.paddings.defaultPadding),
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

@Preview(showBackground = true)
@Composable
fun HeroListScreenPreview() {
    val heroes = SampleData.heroesSample
    MarvelComposeTheme {
        HeroListScreenContent(HeroListState.Success(heroes))
    }
}
