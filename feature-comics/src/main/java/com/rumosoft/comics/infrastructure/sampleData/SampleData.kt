package com.rumosoft.comics.infrastructure.sampleData

import com.rumosoft.comics.data.mappers.toComic
import com.rumosoft.marvelapi.data.network.apimodels.ComicDto
import com.rumosoft.marvelapi.data.network.apimodels.ImageDto

object SampleData {
    val comicsDtoSample = (1..10).map {
        ComicDto(
            id = 0,
            digitalId = 0,
            title = "comic $it",
            description = "description",
            thumbnail = ImageDto(
                path = "path",
                extension = "extension",
            ),
            pageCount = 0,
        )
    }

    val comicsSample = comicsDtoSample.map { it.toComic() }
}
