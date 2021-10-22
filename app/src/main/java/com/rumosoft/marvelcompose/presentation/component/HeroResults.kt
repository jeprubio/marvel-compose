package com.rumosoft.marvelcompose.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumosoft.marvelcompose.R
import com.rumosoft.marvelcompose.domain.model.Hero
import com.rumosoft.marvelcompose.infrastructure.sampleData.SampleData
import com.rumosoft.marvelcompose.presentation.theme.MarvelComposeTheme

@Composable
fun HeroResults(
    heroes: List<Hero>,
    modifier: Modifier = Modifier,
    onClick: (Hero) -> Unit = {},
) {
    LazyColumn(
        modifier = modifier.fillMaxWidth()
    ) {
        items(heroes) { hero ->
            HeroResult(hero, onClick)
            Spacer(modifier = Modifier.padding(8.dp))
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
        HeroImage(
            thumbnailUrl = hero.thumbnail,
            circular = true,
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colors.primary)
                .padding(2.dp)
        )
        Spacer(modifier = Modifier.padding(8.dp))
        Text(
            text = hero.name,
            style = MaterialTheme.typography.subtitle1,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            softWrap = false,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HeroResultPreview() {
    val heroes = remember { listOf(SampleData.batman) }
    MarvelComposeTheme {
        HeroResults(heroes)
    }
}
