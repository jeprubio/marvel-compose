package com.rumosoft.marvelcompose.presentation.viewmodel.state

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumosoft.marvelcompose.R
import com.rumosoft.marvelcompose.domain.model.Hero
import com.rumosoft.marvelcompose.domain.model.NoMoreResultsException
import com.rumosoft.marvelcompose.infrastructure.sampleData.SampleData
import com.rumosoft.marvelcompose.presentation.component.HeroResults
import com.rumosoft.marvelcompose.presentation.theme.MarvelComposeTheme

const val ProgressIndicator = "progressIndicator"
const val ErrorResult = "errorResult"
const val SuccessResult = "successResult"
const val NoResults = "noResults"

const val RetryTag = "retry"

data class HeroListScreenState(
    val heroListState: HeroListState,
    val selectedHero: Hero? = null,
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
                    modifier = Modifier.testTag(ProgressIndicator)
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
                SimpleMessage(message)
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
        val heroes: List<Hero>?,
        private val loadingMore: Boolean = false,
        private val onClick: (Hero) -> Unit = {},
        private val onEndReached: () -> Unit = {},
    ) : HeroListState() {
        @Composable
        override fun BuildUI() {
            heroes?.takeIf { it.isNotEmpty() }?.let {
                HeroResults(
                    heroes = heroes,
                    loadingMore = loadingMore,
                    modifier = Modifier.testTag(SuccessResult),
                    onClick = onClick,
                    onEndReached = onEndReached,
                )
            } ?: run {
                SimpleMessage(
                    stringResource(id = R.string.no_results),
                    modifier = Modifier.testTag(NoResults)
                )
            }
        }
    }

    @Composable
    fun SimpleMessage(
        message: String,
        modifier: Modifier = Modifier
    ) {
        Text(
            text = message,
            color = MarvelComposeTheme.colors.onBackground,
            fontWeight = FontWeight.Bold,
            modifier = modifier.padding(16.dp),
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSearchSuccess() {
    HeroListState.Success(listOf(SampleData.batman)).BuildUI()
}

@Preview(showBackground = true)
@Composable
fun PreviewSearchError() {
    HeroListState.Error(Exception("Whatever")).BuildUI()
}

@Preview(showBackground = true)
@Composable
fun PreviewSearchLoading() {
    HeroListState.Loading
}
