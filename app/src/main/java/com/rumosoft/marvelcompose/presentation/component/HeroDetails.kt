package com.rumosoft.marvelcompose.presentation.component

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.rumosoft.marvelcompose.R
import com.rumosoft.marvelcompose.domain.model.Comic
import com.rumosoft.marvelcompose.domain.model.Hero
import com.rumosoft.marvelcompose.domain.model.Link
import com.rumosoft.marvelcompose.presentation.theme.CustomDiamond
import com.rumosoft.marvelcompose.presentation.theme.MarvelComposeTheme
import com.rumosoft.marvelcompose.presentation.viewmodel.state.SuccessResult
import timber.log.Timber

@Composable
fun HeroDetails(hero: Hero) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(scrollState)
            .testTag(SuccessResult)
            .padding(horizontal = MarvelComposeTheme.paddings.defaultPadding),
    ) {
        val avatarModifier = Modifier
            .align(alignment = Alignment.CenterHorizontally)
            .padding(top = MarvelComposeTheme.paddings.defaultPadding)
            .size(150.dp)
        HeroImage(hero.thumbnail, hero.name, avatarModifier)
        HeroName(hero.name)
        if (hero.links != null) {
            Links(hero.links, Modifier.align(alignment = Alignment.CenterHorizontally))
        }
        Description(hero.description)
        Comics(hero.comics)
    }
}

@Composable
fun HeroImage(
    thumbnail: String,
    contentDescription: String,
    modifier: Modifier = Modifier
) {
    if (thumbnail.isNotEmpty()) {
        MarvelImage(
            thumbnailUrl = thumbnail,
            contentDescription = contentDescription,
            circular = true,
            modifier = modifier,
        )
    } else {
        Image(
            painterResource(id = R.drawable.img_no_image),
            contentDescription = stringResource(id = R.string.no_image),
            modifier = modifier
                .clip(CircleShape),
        )
    }
}

@Composable
private fun HeroName(name: String) {
    Text(
        text = name,
        color = MarvelComposeTheme.colors.primary,
        style = MaterialTheme.typography.h1,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = MarvelComposeTheme.paddings.defaultPadding,
                bottom = MarvelComposeTheme.paddings.tinyPadding,
            )
    )
}

@Composable
fun Links(links: List<Link>, modifier: Modifier = Modifier) {
    LazyRow(
        modifier = modifier
    ) {
        items(links) { url ->
            Urls(url)
        }
    }
}

@Composable
fun Urls(link: Link) {
    val context = LocalContext.current
    val intent = remember { Intent(Intent.ACTION_VIEW, Uri.parse(link.url)) }
    Surface(
        color = MarvelComposeTheme.colors.surface,
        border = BorderStroke(1.dp, MarvelComposeTheme.colors.onSecondary),
        shape = CustomDiamond(10f),
        elevation = 8.dp,
        modifier = Modifier
            .clickable { context.startActivity(intent) }
            .padding(end = MarvelComposeTheme.paddings.smallPadding),
    ) {
        Text(
            text = link.type.uppercase(),
            modifier = Modifier.padding(
                vertical = 2.dp,
                horizontal = 8.dp,
            )
        )
    }
}

@Composable
fun Description(description: String) {
    SectionTitle(stringResource(id = R.string.description))
    Text(
        text = description.takeIf { it.isNotEmpty() }
            ?: stringResource(id = R.string.no_description),
        style = MaterialTheme.typography.body1
    )
}

@Composable
fun Comics(comics: List<Comic>?) {
    SectionTitle(stringResource(id = R.string.comics))
    if (!comics.isNullOrEmpty()) {
        LazyRow {
            items(comics) { comic ->
                comic.thumbnail?.takeIf { it.isNotEmpty() }?.let { thumbnail ->
                    ComicThumbnail(comic.name, thumbnail)
                    Timber.d("thumbnail: $thumbnail")
                }
            }
        }
    }
}

@Composable
fun ComicThumbnail(title: String, thumbnail: String) {
    MarvelImage(
        thumbnailUrl = thumbnail,
        contentScale = ContentScale.Fit,
        contentDescription = title,
        originalSize = true,
        modifier = Modifier
            .height(150.dp)
            .padding(end = MarvelComposeTheme.paddings.medium),
    )
}

@Composable
fun SectionTitle(section: String) {
    Text(
        text = section,
        color = MarvelComposeTheme.colors.primary,
        style = MarvelComposeTheme.typography.subtitle1,
        modifier = Modifier.padding(vertical = MarvelComposeTheme.paddings.medium)
    )
}
