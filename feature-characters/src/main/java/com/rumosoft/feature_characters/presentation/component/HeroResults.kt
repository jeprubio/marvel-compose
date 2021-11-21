package com.rumosoft.feature_characters.presentation.component

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumosoft.commons.domain.model.Hero
import com.rumosoft.components.presentation.component.MarvelImage
import com.rumosoft.components.presentation.theme.MarvelComposeTheme
import com.rumosoft.feature_characters.R
import com.rumosoft.feature_characters.infrastructure.sampleData.SampleData
import timber.log.Timber

@Composable
fun HeroResults(
    heroes: List<Hero>,
    modifier: Modifier = Modifier,
    loadingMore: Boolean = false,
    onClick: (Hero) -> Unit = {},
    onEndReached: () -> Unit = {},
) {
    val lastIndex = heroes.lastIndex
    LazyColumn(
        contentPadding = PaddingValues(MarvelComposeTheme.paddings.defaultPadding),
        verticalArrangement = Arrangement.spacedBy(MarvelComposeTheme.paddings.defaultPadding),
        modifier = modifier.fillMaxWidth()
    ) {
        itemsIndexed(heroes) { index, hero ->
            if (lastIndex == index) {
                LaunchedEffect(Unit) {
                    Timber.d("End element reached")
                    onEndReached.invoke()
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
private fun HeroResult(
    hero: Hero,
    onClick: (Hero) -> Unit = {},
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxSize()
            .clickable(
                onClickLabel = stringResource(id = R.string.show_details)
            ) {
                onClick(hero)
            },
    ) {
        val imageModifier = Modifier
            .size(80.dp)
        HeroImage(hero, imageModifier)
        Spacer(modifier = Modifier.padding(8.dp))
        HeroName(hero)
    }
}

@Composable
private fun HeroImage(
    hero: Hero,
    modifier: Modifier
) {
    if (hero.thumbnail.isNotEmpty()) {
        RemoteImage(hero, modifier)
    } else {
        LocalImage(hero, modifier)
    }
}

@Composable
private fun RemoteImage(
    hero: Hero,
    modifier: Modifier
) {
    MarvelImage(
        thumbnailUrl = hero.thumbnail,
        contentDescription = hero.name,
        modifier = modifier,
    )
}

@Composable
private fun LocalImage(
    hero: Hero,
    modifier: Modifier
) {
    Image(
        painterResource(id = R.drawable.img_no_image),
        contentDescription = hero.name,
        modifier = modifier,
    )
}

@Composable
private fun HeroName(hero: Hero) {
    Text(
        text = hero.name,
        color = MarvelComposeTheme.colors.onBackground,
        style = MarvelComposeTheme.typography.subtitle1,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        softWrap = false,
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
fun HeroResultPreview() {
    val heroes = remember { listOf(SampleData.heroesSample.first()) }
    MarvelComposeTheme {
        HeroResults(heroes)
    }
}
