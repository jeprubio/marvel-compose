package com.rumosoft.feature_characters.infrastructure.sampleData

import com.rumosoft.feature_characters.domain.model.Comic
import com.rumosoft.feature_characters.domain.model.Hero
import com.rumosoft.feature_characters.domain.model.Link

object SampleData {
    val batman = Hero(
        name = "Batman",
        description = "whatever",
        thumbnail = "",
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
        comics = listOf(
            Comic(
                name = "name",
                url = "url/123",
            )
        ),
    )

    val heroesSample = listOf(batman, batman.copy(name = "Spiderman"))
}
