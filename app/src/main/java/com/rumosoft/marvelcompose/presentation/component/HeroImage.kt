package com.rumosoft.marvelcompose.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.rumosoft.marvelcompose.R

@Composable
fun HeroImage(
    thumbnailUrl: String,
    modifier: Modifier = Modifier,
    circular: Boolean = false,
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
            }
        ),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = modifier
    )
}
