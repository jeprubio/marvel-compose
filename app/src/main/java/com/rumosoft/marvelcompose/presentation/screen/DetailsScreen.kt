package com.rumosoft.marvelcompose.presentation.screen

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.rumosoft.marvelcompose.domain.model.Hero
import com.rumosoft.marvelcompose.infrastructure.sampleData.SampleData
import com.rumosoft.marvelcompose.presentation.theme.MarvelComposeTheme
import com.rumosoft.marvelcompose.presentation.viewmodel.DetailsViewModel
import com.rumosoft.marvelcompose.presentation.viewmodel.state.DetailsState

@Composable
fun DetailsScreen(navController: NavController, viewModel: DetailsViewModel, hero: Hero?) {
    val screenState by viewModel.detailsState.collectAsState()
    hero?.let {
        viewModel.setHero(hero)
    }
    DetailsScreenContent(screenState)
}

@Composable
private fun DetailsScreenContent(detailsState: DetailsState) {
    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        detailsState.BuildUI()
    }
}

@Preview(showBackground = true)
@Composable
fun DetailsPreviewSuccess() {
    MarvelComposeTheme {
        DetailsScreenContent(DetailsState.Success(SampleData.batman))
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun DetailsPreviewSuccessDarkMode() {
    MarvelComposeTheme {
        DetailsScreenContent(DetailsState.Success(SampleData.batman))
    }
}

@Preview(showBackground = true)
@Composable
fun DetailsPreviewLoading() {
    MarvelComposeTheme {
        DetailsScreenContent(DetailsState.Loading)
    }
}

@Preview(showBackground = true, device = Devices.AUTOMOTIVE_1024p, widthDp = 1440, heightDp = 720)
@Composable
fun DetailsPreviewSuccessLandscape() {
    MarvelComposeTheme {
        DetailsScreenContent(DetailsState.Success(SampleData.batman))
    }
}
