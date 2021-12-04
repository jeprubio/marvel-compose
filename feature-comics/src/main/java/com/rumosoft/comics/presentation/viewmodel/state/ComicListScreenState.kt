package com.rumosoft.comics.presentation.viewmodel.state

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.rumosoft.comics.R
import com.rumosoft.comics.infrastructure.sampleData.SampleData
import com.rumosoft.comics.presentation.component.ComicResults
import com.rumosoft.commons.domain.model.Comic
import com.rumosoft.commons.domain.model.NoMoreResultsException
import com.rumosoft.components.presentation.component.SimpleMessage
import com.rumosoft.components.presentation.theme.MarvelComposeTheme

@ExperimentalFoundationApi
data class ComicListScreenState(
    val comicListState: ComicListState,
    val showingSearchBar: Boolean = false,
    val selectedComic: Comic? = null,
    val textSearched: String = "",
)

@ExperimentalFoundationApi
sealed class ComicListState {
    companion object {
        const val ProgressIndicator = "progressIndicator"
        const val ErrorResult = "errorResult"
        const val SuccessResult = "successResult"
        const val NoResults = "noResults"
        const val RetryTag = "retry"
    }

    @Composable
    abstract fun BuildUI()

    object Loading : ComicListState() {
        @Composable
        override fun BuildUI() {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize(),
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.testTag(ProgressIndicator)
                )
            }
        }
    }

    class Error(
        private val throwable: Throwable,
        private val retry: () -> Unit = {},
    ) : ComicListState() {
        @Composable
        override fun BuildUI() {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .testTag(ErrorResult),
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
                    modifier = Modifier.testTag(RetryTag),
                ) {
                    Text(text = stringResource(id = R.string.retry))
                }
            }
        }
    }

    class Success(
        val comics: List<Comic>?,
        private val loadingMore: Boolean = false,
        private val onClick: (Comic) -> Unit = {},
        private val onEndReached: () -> Unit = {},
    ) : ComicListState() {
        @Composable
        override fun BuildUI() {
            comics?.takeIf { it.isNotEmpty() }?.let {
                ComicResults(
                    comics = comics,
                    loadingMore = loadingMore,
                    modifier = Modifier.testTag(SuccessResult),
                    onClick = onClick,
                    onEndReached = onEndReached,
                )
            } ?: run {
                SimpleMessage(
                    message = stringResource(id = R.string.no_results),
                    modifier = Modifier
                        .fillMaxSize()
                        .testTag(NoResults),
                )
            }
        }
    }
}

@ExperimentalFoundationApi
@Preview(showBackground = true)
@Preview(
    showBackground = true,
    uiMode = UI_MODE_NIGHT_YES,
)
@Composable
fun PreviewSearchSuccess() {
    MarvelComposeTheme {
        ComicListState.Success(listOf(SampleData.comicsSample.first())).BuildUI()
    }
}

@ExperimentalFoundationApi
@Preview(showBackground = true)
@Preview(
    showBackground = true,
    uiMode = UI_MODE_NIGHT_YES,
)
@Composable
fun PreviewSearchError() {
    MarvelComposeTheme {
        ComicListState.Error(Exception("Whatever")).BuildUI()
    }
}

@ExperimentalFoundationApi
@Preview(showBackground = true)
@Composable
fun PreviewSearchLoading() {
    MarvelComposeTheme {
        ComicListState.Loading
    }
}
