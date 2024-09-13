package com.rumosoft.characters.data.mappers

import com.rumosoft.characters.domain.model.Character
import com.rumosoft.characters.domain.model.ComicSummary
import com.rumosoft.characters.domain.model.Link
import com.rumosoft.marvelapi.data.network.apimodels.ComicSummaryDto
import com.rumosoft.marvelapi.data.network.apimodels.HeroDto
import com.rumosoft.marvelapi.data.network.apimodels.UrlDto
import com.rumosoft.marvelapi.data.network.apimodels.toThumbnailUrl

fun HeroDto.toHero(): Character {
    return Character(
        id = id,
        name = name.orEmpty(),
        description = description.orEmpty(),
        thumbnail = thumbnail?.toThumbnailUrl().orEmpty(),
        links = urls?.map { it.toUrl() }.orEmpty(),
        comics = comics?.items?.map { it.toComicSummary() }.orEmpty(),
    )
}

fun UrlDto.toUrl(): Link =
    Link(
        type = type.orEmpty(),
        url = url.orEmpty(),
    )

fun ComicSummaryDto.toComicSummary(): ComicSummary =
    ComicSummary(
        title = name.orEmpty(),
        url = resourceUri.orEmpty(),
    )
