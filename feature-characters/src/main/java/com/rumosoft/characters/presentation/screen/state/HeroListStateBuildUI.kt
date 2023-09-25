package com.rumosoft.characters.presentation.screen.state

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.rumosoft.characters.infrastructure.sampleData.SampleData
import com.rumosoft.characters.presentation.component.HeroResults
import com.rumosoft.characters.presentation.viewmodel.state.HeroListErrorResult
import com.rumosoft.characters.presentation.viewmodel.state.HeroListNoResults
import com.rumosoft.characters.presentation.viewmodel.state.HeroListProgressIndicator
import com.rumosoft.characters.presentation.viewmodel.state.HeroListState
import com.rumosoft.characters.presentation.viewmodel.state.HeroListState.Error
import com.rumosoft.characters.presentation.viewmodel.state.HeroListState.Loading
import com.rumosoft.characters.presentation.viewmodel.state.HeroListState.Success
import com.rumosoft.characters.presentation.viewmodel.state.HeroListSuccessResult
import com.rumosoft.components.presentation.component.CustomLoading
import com.rumosoft.components.presentation.component.ErrorMessage
import com.rumosoft.components.presentation.component.SimpleMessage
import com.rumosoft.components.presentation.theme.MarvelComposeTheme

@Composable
fun HeroListState.BuildUI() {
    when (this) {
        is Loading -> BuildLoading()
        is Error -> BuildError()
        is Success -> BuildSuccess()
    }
}

@Composable
private fun BuildLoading() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .testTag(HeroListProgressIndicator),
    ) {
        CustomLoading()
    }
}

@Composable
private fun Error.BuildError() {
    val message = stringResource(id = com.rumosoft.components.R.string.error_data_message)
    ErrorMessage(
        message = message,
        modifier = Modifier.testTag(HeroListErrorResult),
        onRetry = retry,
    )
}

@Composable
private fun Success.BuildSuccess() {
    characters?.takeIf { it.isNotEmpty() }?.let {
        HeroResults(
            characters = characters,
            loadingMore = loadingMore,
            modifier = Modifier.testTag(HeroListSuccessResult),
            onClick = onClick,
            onEndReached = onEndReached,
        )
    } ?: run {
        SimpleMessage(
            message = stringResource(id = com.rumosoft.components.R.string.no_results),
            modifier = Modifier
                .fillMaxSize()
                .testTag(HeroListNoResults),
        )
    }
}

@Preview(showBackground = true)
@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
fun PreviewSearchSuccess() {
    MarvelComposeTheme {
        Success(listOf(SampleData.heroesSample.first())).BuildUI()
    }
}

@Preview(showBackground = true)
@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
fun PreviewSearchError() {
    MarvelComposeTheme {
        Error(Exception("Whatever")).BuildUI()
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSearchLoading() {
    MarvelComposeTheme {
        Loading
    }
}
