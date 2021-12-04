package com.rumosoft.feature_characters.presentation.viewmodel.state

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
import com.rumosoft.commons.domain.model.Character
import com.rumosoft.commons.domain.model.NoMoreResultsException
import com.rumosoft.components.presentation.component.ErrorMessage
import com.rumosoft.components.presentation.component.SimpleMessage
import com.rumosoft.components.presentation.theme.MarvelComposeTheme
import com.rumosoft.feature_characters.R
import com.rumosoft.feature_characters.infrastructure.sampleData.SampleData
import com.rumosoft.feature_characters.presentation.component.HeroResults

const val HeroListProgressIndicator = "progressIndicator"
const val HeroListErrorResult = "errorResult"
const val HeroListSuccessResult = "successResult"
const val HeroListNoResults = "noResults"

data class HeroListScreenState(
    val heroListState: HeroListState,
    val showingSearchBar: Boolean = false,
    val selectedCharacter: Character? = null,
    val textSearched: String = "",
)

sealed class HeroListState {
    @Composable
    abstract fun BuildUI()

    object Loading : HeroListState() {
        @Composable
        override fun BuildUI() {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize(),
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.testTag(HeroListProgressIndicator)
                )
            }
        }
    }

    class Error(
        private val throwable: Throwable,
        private val retry: () -> Unit = {},
    ) : HeroListState() {
        @Composable
        override fun BuildUI() {
            val message = if (throwable is NoMoreResultsException) {
                stringResource(id = R.string.no_more_results)
            } else {
                stringResource(id = R.string.error_data_message)
            }
            ErrorMessage(
                message = message,
                modifier = Modifier.testTag(HeroListErrorResult),
                onRetry = retry,
            )
        }
    }

    class Success(
        val characters: List<Character>?,
        private val loadingMore: Boolean = false,
        private val onClick: (Character) -> Unit = {},
        private val onEndReached: () -> Unit = {},
    ) : HeroListState() {
        @Composable
        override fun BuildUI() {
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
                    message = stringResource(id = R.string.no_results),
                    modifier = Modifier
                        .fillMaxSize()
                        .testTag(HeroListNoResults),
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Preview(
    showBackground = true,
    uiMode = UI_MODE_NIGHT_YES,
)
@Composable
fun PreviewSearchSuccess() {
    MarvelComposeTheme {
        HeroListState.Success(listOf(SampleData.heroesSample.first())).BuildUI()
    }
}

@Preview(showBackground = true)
@Preview(
    showBackground = true,
    uiMode = UI_MODE_NIGHT_YES,
)
@Composable
fun PreviewSearchError() {
    MarvelComposeTheme {
        HeroListState.Error(Exception("Whatever")).BuildUI()
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSearchLoading() {
    MarvelComposeTheme {
        HeroListState.Loading
    }
}
