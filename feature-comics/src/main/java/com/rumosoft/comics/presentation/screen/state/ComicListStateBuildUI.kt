package com.rumosoft.comics.presentation.screen.state

import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.rumosoft.comics.R
import com.rumosoft.comics.infrastructure.sampleData.SampleData
import com.rumosoft.comics.presentation.component.ComicResults
import com.rumosoft.comics.presentation.viewmodel.state.ComicListState
import com.rumosoft.comics.presentation.viewmodel.state.ComicListState.Error
import com.rumosoft.comics.presentation.viewmodel.state.ComicListState.Loading
import com.rumosoft.comics.presentation.viewmodel.state.ComicListState.Success
import com.rumosoft.commons.domain.model.NoMoreResultsException
import com.rumosoft.components.presentation.component.CustomLoading
import com.rumosoft.components.presentation.component.SimpleMessage
import com.rumosoft.components.presentation.theme.MarvelComposeTheme

@ExperimentalFoundationApi
@Composable
fun ComicListState.BuildUI() {
    when (this) {
        is Loading -> BuildLoading()
        is Error -> BuildError()
        is Success -> BuildSuccess()
    }
}

@ExperimentalFoundationApi
@Composable
private fun BuildLoading() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .testTag(ComicListState.ProgressIndicator),
    ) {
        CustomLoading()
    }
}

@ExperimentalFoundationApi
@Composable
private fun Error.BuildError() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .testTag(ComicListState.ErrorResult),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        val message = if (throwable is NoMoreResultsException) {
            stringResource(id = R.string.no_more_results)
        } else {
            stringResource(id = R.string.error_data_message)
        }
        SimpleMessage(
            message = message,
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(modifier = Modifier.padding(top = MarvelComposeTheme.paddings.defaultPadding))
        Button(
            onClick = { retry.invoke() },
            modifier = Modifier.testTag(ComicListState.RetryTag),
        ) {
            Text(text = stringResource(id = R.string.retry))
        }
    }
}

@ExperimentalFoundationApi
@Composable
private fun Success.BuildSuccess() {
    comics?.takeIf { it.isNotEmpty() }?.let {
        ComicResults(
            comics = comics,
            loadingMore = loadingMore,
            modifier = Modifier.testTag(ComicListState.SuccessResult),
            onClick = onClick,
            onEndReached = onEndReached,
        )
    } ?: run {
        SimpleMessage(
            message = stringResource(id = R.string.no_results),
            modifier = Modifier
                .fillMaxSize()
                .testTag(ComicListState.NoResults),
        )
    }
}

@ExperimentalFoundationApi
@Preview(showBackground = true)
@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
fun PreviewSearchSuccess() {
    MarvelComposeTheme {
        Success(listOf(SampleData.comicsSample.first())).BuildUI()
    }
}

@ExperimentalFoundationApi
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

@ExperimentalFoundationApi
@Preview(showBackground = true)
@Composable
fun PreviewSearchLoading() {
    MarvelComposeTheme {
        Loading
    }
}
