package com.rumosoft.comics.presentation.component

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumosoft.comics.R
import com.rumosoft.comics.infrastructure.sampleData.SampleData
import com.rumosoft.commons.domain.model.Comic
import com.rumosoft.components.presentation.component.ComicThumbnail
import com.rumosoft.components.presentation.theme.MarvelComposeTheme
import timber.log.Timber

@Composable
fun ComicResults(
    comics: List<Comic>,
    modifier: Modifier = Modifier,
    loadingMore: Boolean = false,
    onClick: (Comic) -> Unit = {},
    onEndReached: () -> Unit = {},
) {
    val lastIndex = comics.lastIndex
    LazyColumn(
        contentPadding = PaddingValues(MarvelComposeTheme.paddings.defaultPadding),
        verticalArrangement = Arrangement.spacedBy(MarvelComposeTheme.paddings.defaultPadding),
        modifier = modifier.fillMaxWidth()
    ) {
        itemsIndexed(comics) { index, comic ->
            if (lastIndex == index) {
                LaunchedEffect(Unit) {
                    Timber.d("End element reached")
                    onEndReached.invoke()
                }
            }
            ComicResult(comic, onClick)
        }
        if (loadingMore) {
            item {
                Loading()
            }
        }
    }
}

@Composable
private fun ComicResult(
    comic: Comic,
    onClick: (Comic) -> Unit = {},
) {
    Column(
        modifier = Modifier
            .clickable(
                onClickLabel = stringResource(id = R.string.show_details)
            ) {
                onClick(comic)
            },
    ) {
        comic.thumbnail?.let {
            ComicThumbnail(title = comic.name, thumbnail = it)
        }
        ComicName(comic)
    }
}

@Composable
private fun ComicName(
    comic: Comic,
    modifier: Modifier = Modifier,
) {
    Text(
        comic.name,
        modifier = modifier,
    )
}

@Composable
private fun Loading() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        CircularProgressIndicator()
    }
}

@Preview(showBackground = true)
@Preview(
    showBackground = true,
    uiMode = UI_MODE_NIGHT_YES,
)
@Composable
fun ComicResultsPreview() {
    val comics = remember { SampleData.comicsSample }
    MarvelComposeTheme {
        ComicResults(comics = comics)
    }
}
