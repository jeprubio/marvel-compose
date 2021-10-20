package com.rumosoft.marvelcompose.presentation.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.rumosoft.marvelcompose.domain.model.Hero
import com.rumosoft.marvelcompose.infrastructure.sampleData.SampleData
import com.rumosoft.marvelcompose.presentation.theme.MarvelComposeTheme

@Composable
fun HeroResult(heroes: List<Hero>) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth()
    ) {
        items(heroes) { hero ->
            Text(text = hero.name)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HeroResultPreview() {
    val heroes = remember { listOf(SampleData.batman) }
    MarvelComposeTheme {
        HeroResult(heroes)
    }
}
