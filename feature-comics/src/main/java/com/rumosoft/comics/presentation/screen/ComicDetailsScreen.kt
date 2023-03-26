package com.rumosoft.comics.presentation.screen

import android.content.res.Configuration
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.rumosoft.comics.R
import com.rumosoft.comics.infrastructure.sampleData.SampleData
import com.rumosoft.comics.presentation.screen.state.BuildUI
import com.rumosoft.comics.presentation.viewmodel.ComicDetailsViewModel
import com.rumosoft.comics.presentation.viewmodel.state.ComicDetailsState
import com.rumosoft.components.presentation.component.TopBar
import com.rumosoft.components.presentation.theme.MarvelComposeTheme

@Composable
fun ComicDetailsScreen(
    viewModel: ComicDetailsViewModel,
    onBackPressed: () -> Unit,
) {
    val screenState by viewModel.detailsState.collectAsState()
    ComicDetailsScreenContent(screenState, onBackPressed)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComicDetailsScreenContent(
    screenState: ComicDetailsState,
    onBackPressed: () -> Unit = {},
) {
    Scaffold(
        topBar = {
            TopBar(
                apBarText = stringResource(R.string.comic_details),
                leftIcon = painterResource(id = R.drawable.ic_arrow_back_24),
                leftIconPressed = onBackPressed,
            )
        },
    ) { padding ->
        screenState.BuildUI(
            modifier = Modifier.padding(padding),
        )
    }
}

@Preview(showBackground = true)
@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
fun ComicDetailsScreenPreview() {
    MarvelComposeTheme {
        ComicDetailsScreenContent(ComicDetailsState.Success(SampleData.comicsSample.first()))
    }
}
