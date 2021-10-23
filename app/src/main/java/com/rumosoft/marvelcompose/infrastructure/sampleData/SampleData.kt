package com.rumosoft.marvelcompose.infrastructure.sampleData

import com.rumosoft.marvelcompose.domain.model.Comic
import com.rumosoft.marvelcompose.domain.model.Hero
import com.rumosoft.marvelcompose.domain.model.Link

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
