package com.rumosoft.characters.presentation.screen

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rumosoft.characters.R
import com.rumosoft.characters.infrastructure.sampleData.SampleData
import com.rumosoft.characters.presentation.screen.state.BuildUI
import com.rumosoft.characters.presentation.viewmodel.DetailsViewModel
import com.rumosoft.characters.presentation.viewmodel.state.DetailsState
import com.rumosoft.components.presentation.component.TopBar
import com.rumosoft.components.presentation.theme.MarvelComposeTheme

@Composable
fun CharacterDetailsTopBar(
    onBackPressed: () -> Unit,
    modifier: Modifier = Modifier,
) {
    TopBar(
        apBarText = stringResource(R.string.details),
        leftIcon = painterResource(id = com.rumosoft.components.R.drawable.ic_arrow_back_24),
        leftIconContentDescription = stringResource(com.rumosoft.components.R.string.go_back),
        leftIconPressed = onBackPressed,
        modifier = modifier,
    )
}

@Composable
fun DetailsScreen(
    viewModel: DetailsViewModel,
    onComicSelected: (Int) -> Unit,
) {
    val screenState by viewModel.detailsState.collectAsStateWithLifecycle()
    DetailsScreenContent(screenState, onComicSelected)
}

@Composable
internal fun DetailsScreenContent(
    detailsState: DetailsState,
    onComicSelected: (Int) -> Unit = {},
) {
    detailsState.BuildUI(onComicSelected)
}

@Preview(showBackground = true)
@Preview(
    showBackground = true,
    uiMode = UI_MODE_NIGHT_YES,
)
@Composable
fun DetailsPreviewSuccess() {
    MarvelComposeTheme {
        DetailsScreenContent(DetailsState.Success(SampleData.heroesSample.first()))
    }
}

@Preview(showBackground = true)
@Composable
fun DetailsPreviewLoading() {
    MarvelComposeTheme {
        DetailsScreenContent(DetailsState.Loading)
    }
}

@Preview(
    showBackground = true,
    device = Devices.AUTOMOTIVE_1024p,
    widthDp = 1440,
    heightDp = 720,
)
@Preview(
    showBackground = true,
    device = Devices.AUTOMOTIVE_1024p,
    widthDp = 1440,
    heightDp = 720,
    uiMode = UI_MODE_NIGHT_YES,
)
@Composable
fun DetailsPreviewSuccessLandscape() {
    MarvelComposeTheme {
        DetailsScreenContent(DetailsState.Success(SampleData.heroesSample.first()))
    }
}
