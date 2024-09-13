package com.rumosoft.characters.infrastructure.sampleData

import com.rumosoft.characters.data.mappers.toHero
import com.rumosoft.marvelapi.data.network.apimodels.ComicSummaryDto
import com.rumosoft.marvelapi.data.network.apimodels.ComicsDto
import com.rumosoft.marvelapi.data.network.apimodels.HeroDto
import com.rumosoft.marvelapi.data.network.apimodels.ImageDto
import com.rumosoft.marvelapi.data.network.apimodels.UrlDto

object SampleData {
    val heroesDtoSample = (1..10).map {
        HeroDto(
            id = it.toLong(),
            name = "Hero$it",
            description = "Description hero $it",
            thumbnail = ImageDto(
                path = "http://lorempixel.com/150/150/people/$it/",
                extension = "jpg",
            ),
            urls = listOf(
                UrlDto(
                    type = "Detail",
                    url = "DetailUrl",
                ),
                UrlDto(
                    type = "Wiki",
                    url = "wikiUrl",
                ),
                UrlDto(
                    type = "ComicLink",
                    url = "comicLink",
                ),
            ),
            comics = ComicsDto(
                available = 2,
                returned = 2,
                collectionUri = "collectionUri",
                items = (1..2).map {
                    ComicSummaryDto(
                        name = "comic $it",
                        resourceUri = "url/comic/$it",
                    )
                },
            ),
        )
    }


    val heroesSample = heroesDtoSample.map { it.toHero() }

    val heroesSampleWithoutImages = heroesSample.map { hero ->
        hero.copy(
            thumbnail = "",
            comics = hero.comics?.map {
                it.copy(thumbnail = "")
            },
        )
    }
}
