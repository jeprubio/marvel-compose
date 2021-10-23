package com.rumosoft.marvelcompose.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil.compose.rememberImagePainter
import coil.size.OriginalSize
import coil.transform.CircleCropTransformation
import com.rumosoft.marvelcompose.R

@Composable
fun MarvelImage(
    thumbnailUrl: String,
    modifier: Modifier = Modifier,
    circular: Boolean = false,
    contentDescription: String? = null,
    contentScale: ContentScale = ContentScale.Crop,
) {
    Image(
        painter = rememberImagePainter(
            data = thumbnailUrl,
            builder = {
                if (circular) {
                    transformations(
                        CircleCropTransformation()
                    )
                }
                crossfade(true)
                placeholder(R.mipmap.ic_launcher)
                size(OriginalSize)
            }
        ),
        contentDescription = contentDescription,
        contentScale = contentScale,
        modifier = modifier
    )
}
