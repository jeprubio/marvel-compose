package com.rumosoft.comics.presentation.viewmodel.state

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.rumosoft.comics.R
import com.rumosoft.comics.infrastructure.sampleData.SampleData
import com.rumosoft.comics.presentation.component.ComicDetails
import com.rumosoft.commons.domain.model.Comic
import com.rumosoft.commons.domain.model.NoMoreResultsException
import com.rumosoft.components.presentation.component.CustomLoading
import com.rumosoft.components.presentation.component.ErrorMessage
import com.rumosoft.components.presentation.theme.MarvelComposeTheme

const val ComicDetailsProgressIndicator = "comicDetailsProgressIndicator"
const val ComicDetailsErrorResult = "comicDetailsErrorResult"
const val ComicDetailsSuccessResult = "comicDetailsSuccessResult"

sealed class ComicDetailsState {
    @Composable
    abstract fun BuildUI()

    object Loading : ComicDetailsState() {
        @Composable
        override fun BuildUI() {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .testTag(ComicDetailsProgressIndicator),
            ) {
                CustomLoading()
            }
        }
    }

    class Error(
        private val throwable: Throwable,
        private val retry: () -> Unit = {},
    ) : ComicDetailsState() {
        @Composable
        override fun BuildUI() {
            val message = if (throwable is NoMoreResultsException) {
                stringResource(id = R.string.no_more_results)
            } else {
                stringResource(id = R.string.error_data_message)
            }
            ErrorMessage(
                message = message,
                modifier = Modifier.testTag(ComicDetailsErrorResult),
                onRetry = retry,
            )
        }
    }

    class Success(val comic: Comic) : ComicDetailsState() {
        @Composable
        override fun BuildUI() {
            ComicDetails(comic)
        }
    }
}

@Preview(showBackground = true)
@Preview(
    showBackground = true,
    uiMode = UI_MODE_NIGHT_YES,
)
@Composable
fun PreviewDetailsSuccess() {
    MarvelComposeTheme {
        ComicDetailsState.Success(SampleData.comicsSample.first()).BuildUI()
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDetailsLoading() {
    MarvelComposeTheme {
        ComicDetailsState.Loading
    }
}
