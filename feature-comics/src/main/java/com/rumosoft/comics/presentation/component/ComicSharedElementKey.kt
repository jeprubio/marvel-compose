package com.rumosoft.comics.presentation.component

import com.rumosoft.comics.domain.model.Comic

// Typed key so list and details can't drift apart or collide with other features.
data class ComicImageKey(val comicId: Int)

fun Comic.imageSharedKey(): ComicImageKey = ComicImageKey(id)
