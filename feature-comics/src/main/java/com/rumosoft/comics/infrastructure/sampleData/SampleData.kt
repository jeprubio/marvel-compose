package com.rumosoft.comics.infrastructure.sampleData

import com.rumosoft.commons.domain.model.Comic
import com.rumosoft.commons.domain.model.ComicSummary

object SampleData {
    val comicSummariesSample = (1..10).map {
        ComicSummary(
            title = "comic $it",
            url = "url/comic/$it",
            thumbnail = "",
        )
    }

    val comicsSample = (1..10).map {
        Comic(
            id = 0,
            digitalId = 0,
            title = "comic $it",
            description = "description",
            thumbnail = "",
            pageCount = 0,
            urls = listOf("url/comic/$it"),
            characters = listOf(),
        )
    }
}
