package com.rumosoft.marvelcompose.presentation.viewmodel.state

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumosoft.marvelcompose.R
import com.rumosoft.marvelcompose.domain.model.Hero
import com.rumosoft.marvelcompose.infrastructure.sampleData.SampleData
import com.rumosoft.marvelcompose.presentation.component.HeroResult

data class HeroListScreenState(val heroListResult: HeroListResult)

sealed class HeroListResult {
    @Composable
    abstract fun BuildUI()

    object Loading : HeroListResult() {
        @Composable
        override fun BuildUI() {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                CircularProgressIndicator()
            }
        }
    }

    class Error(private val throwable: Throwable, private val retry: () -> Unit) : HeroListResult() {
        @Composable
        override fun BuildUI() {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                SimpleMessage(stringResource(id = R.string.error_data_message))
            }
        }
    }

    class Success(private val heroes: List<Hero>?) : HeroListResult() {
        @Composable
        override fun BuildUI() {
            heroes?.takeIf { it.isNotEmpty() }?.let {
                HeroResult(heroes = heroes)
            } ?: run {
                SimpleMessage(stringResource(id = R.string.no_results))
            }
        }
    }

    @Composable
    fun SimpleMessage(message: String) {
        Text(
            text = message,
            color = MaterialTheme.colors.onBackground,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(16.dp),
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSearchSuccess() {
    HeroListResult.Success(listOf(SampleData.batman)).BuildUI()
}

@Preview(showBackground = true)
@Composable
fun PreviewSearchError() {
    HeroListResult.Error(Exception("Whatever")) {}.BuildUI()
}

@Preview(showBackground = true)
@Composable
fun PreviewSearchLoading() {
    HeroListResult.Loading
}
