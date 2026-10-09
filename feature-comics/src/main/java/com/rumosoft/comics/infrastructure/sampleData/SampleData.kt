package com.rumosoft.comics.infrastructure.sampleData

import com.rumosoft.comics.data.mappers.toComic
import com.rumosoft.marvelapi.data.network.apimodels.ComicDto
import com.rumosoft.marvelapi.data.network.apimodels.ImageDto

object SampleData {
    fun comicsDtoSample(count: Int = 10) = (1..count).map {
        ComicDto(
            id = it,
            digitalId = it,
            title = "comic $it",
            description = "description",
            thumbnail = ImageDto(
                path = "path",
                extension = "extension",
            ),
            pageCount = 0,
        )
    }

    fun comicsSample(count: Int = 10) = comicsDtoSample(count).map { it.toComic() }
}
