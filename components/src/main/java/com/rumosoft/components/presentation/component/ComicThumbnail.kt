package com.rumosoft.components.presentation.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.rumosoft.components.R
import com.rumosoft.components.presentation.theme.MarvelComposeTheme

@Composable
fun ComicThumbnail(
    title: String,
    thumbnail: String,
    url: String,
    modifier: Modifier = Modifier,
    onComicSelected: ((Int) -> Unit)? = null
) {
    MarvelImage(
        thumbnailUrl = thumbnail,
        contentScale = ContentScale.Fit,
        contentDescription = title,
        originalSize = true,
        noImage = R.drawable.img_no_image,
        modifier = modifier
            .then(
                if (onComicSelected != null) {
                    Modifier.clickable {
                        onComicSelected.invoke(getComicId(url))
                    }
                } else Modifier
            )
            .height(150.dp)
            .padding(end = MarvelComposeTheme.paddings.medium)
            .clip(RoundedCornerShape(4.dp)),
    )
}

private fun getComicId(url: String) = url
    .split("/")
    .last()
    .toInt()
