package com.rumosoft.feature_characters.infrastructure.sampleData

import com.rumosoft.commons.domain.model.Comic
import com.rumosoft.commons.domain.model.Character
import com.rumosoft.commons.domain.model.Link

object SampleData {

    val heroesSample = (1..10).map {
        Character(
            name = "Hero$it",
            description = "Description hero $it",
            thumbnail = "http://lorempixel.com/150/150/people/$it/",
            links = listOf(
                Link(
                    type = "Detail",
                    url = "DetailUrl",
                ),
                Link(
                    type = "Wiki",
                    url = "wikiUrl",
                ),
                Link(
                    type = "ComicLink",
                    url = "comicLink",
                ),
            ),
            comics = (1..2).map {
                Comic(
                    name = "comic $it",
                    url = "url/comic/$it",
                    thumbnail = "http://lorempixel.com/150/150/cats/$it/",
                )
            },
        )
    }

    val heroesSampleWithoutImages = heroesSample.map { hero ->
        hero.copy(
            thumbnail = "",
            comics = hero.comics?.map {
                it.copy(thumbnail = "")
            }
        )
    }
}
