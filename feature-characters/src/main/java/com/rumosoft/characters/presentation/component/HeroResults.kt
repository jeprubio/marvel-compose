package com.rumosoft.characters.presentation.component

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumosoft.characters.R
import com.rumosoft.characters.infrastructure.sampleData.SampleData
import com.rumosoft.characters.presentation.component.semantics.numColumns
import com.rumosoft.commons.domain.model.Character
import com.rumosoft.components.presentation.component.MarvelImage
import com.rumosoft.components.presentation.theme.MarvelComposeTheme
import timber.log.Timber

@Composable
fun HeroResults(
    characters: List<Character>,
    modifier: Modifier = Modifier,
    loadingMore: Boolean = false,
    onClick: (Character) -> Unit = {},
    onEndReached: () -> Unit = {},
) {
    val columns = getDeviceColumns()
    val lastIndex = characters.lastIndex
    LazyVerticalGrid(
        columns = GridCells.Fixed(columns),
        contentPadding = PaddingValues(MarvelComposeTheme.paddings.defaultPadding),
        modifier = modifier
            .fillMaxSize()
            .semantics { numColumns = columns },
    ) {
        itemsIndexed(characters) { index, hero ->
            if (lastIndex == index) {
                LaunchedEffect(Unit) {
                    onLastElementReached(onEndReached)
                }
            }
            HeroResult(hero, onClick)
        }
        if (loadingMore) {
            item {
                Loading()
            }
        }
    }
}

@Composable
private fun getDeviceColumns(): Int {
    val screenWidth = LocalConfiguration.current.screenWidthDp
    return if (screenWidth >= 800) {
        3
    } else if (screenWidth >= 600) {
        2
    } else {
        1
    }
}

private fun onLastElementReached(onEndReached: () -> Unit) {
    Timber.d("End element reached")
    onEndReached.invoke()
}

@Composable
private fun HeroResult(
    character: Character,
    onClick: (Character) -> Unit = {},
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxSize()
            .clickable(
                onClickLabel = stringResource(id = com.rumosoft.components.R.string.show_details),
            ) {
                onClick(character)
            }
            .padding(MarvelComposeTheme.paddings.smallPadding),
    ) {
        val imageModifier = Modifier
            .size(80.dp)
        HeroImage(character, imageModifier)
        Spacer(modifier = Modifier.padding(8.dp))
        HeroName(character)
    }
}

@Composable
private fun HeroImage(
    character: Character,
    modifier: Modifier,
) {
    if (character.thumbnail.isNotEmpty()) {
        RemoteImage(character, modifier)
    } else {
        LocalImage(character, modifier)
    }
}

@Composable
private fun RemoteImage(
    character: Character,
    modifier: Modifier,
) {
    MarvelImage(
        thumbnailUrl = character.thumbnail,
        contentDescription = character.name,
        modifier = modifier,
    )
}

@Composable
private fun LocalImage(
    character: Character,
    modifier: Modifier,
) {
    Image(
        painterResource(id = com.rumosoft.components.R.drawable.img_no_image),
        contentDescription = character.name,
        modifier = modifier,
    )
}

@Composable
private fun HeroName(character: Character) {
    Text(
        text = character.name,
        color = MarvelComposeTheme.colors.onBackground,
        style = MarvelComposeTheme.typography.titleMedium,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        softWrap = false,
    )
}

@Composable
private fun Loading() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxWidth(),
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
fun HeroResultPreview() {
    val heroes = remember { listOf(SampleData.heroesSample.first()) }
    MarvelComposeTheme {
        HeroResults(heroes)
    }
}
