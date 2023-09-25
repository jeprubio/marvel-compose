package com.rumosoft.comics.presentation.screen.state

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.rumosoft.comics.infrastructure.sampleData.SampleData
import com.rumosoft.comics.presentation.component.ComicDetails
import com.rumosoft.comics.presentation.viewmodel.state.ComicDetailsErrorResult
import com.rumosoft.comics.presentation.viewmodel.state.ComicDetailsProgressIndicator
import com.rumosoft.comics.presentation.viewmodel.state.ComicDetailsState
import com.rumosoft.comics.presentation.viewmodel.state.ComicDetailsState.Error
import com.rumosoft.comics.presentation.viewmodel.state.ComicDetailsState.Loading
import com.rumosoft.comics.presentation.viewmodel.state.ComicDetailsState.Success
import com.rumosoft.components.presentation.component.CustomLoading
import com.rumosoft.components.presentation.component.ErrorMessage
import com.rumosoft.components.presentation.theme.MarvelComposeTheme

@Composable
fun ComicDetailsState.BuildUI(
    modifier: Modifier = Modifier,
) {
    when (this) {
        is Loading -> BuildLoading()
        is Error -> BuildError()
        is Success -> ComicDetails(comic, modifier)
    }
}

@Composable
private fun BuildLoading() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .testTag(ComicDetailsProgressIndicator),
    ) {
        CustomLoading()
    }
}

@Composable
private fun Error.BuildError() {
    val message = stringResource(id = com.rumosoft.components.R.string.error_data_message)
    ErrorMessage(
        message = message,
        modifier = Modifier.testTag(ComicDetailsErrorResult),
        onRetry = retry,
    )
}

@Preview(showBackground = true)
@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
fun PreviewDetailsSuccess() {
    MarvelComposeTheme {
        Success(SampleData.comicsSample.first()).BuildUI()
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDetailsLoading() {
    MarvelComposeTheme {
        Loading
    }
}
