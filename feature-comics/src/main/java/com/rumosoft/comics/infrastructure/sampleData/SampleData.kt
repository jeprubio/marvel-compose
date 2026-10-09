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
                path = "https://picsum.photos/seed/marvel-comic-$it/150/150",
                extension = "jpg",
            ),
            pageCount = 0,
        )
    }

    fun comicsSample(count: Int = 10) = comicsDtoSample(count).map { it.toComic() }

    fun comicsSampleWithoutImages(count: Int = 10) =
        comicsSample(count).map { comic ->
            comic.copy(thumbnail = "")
        }
}
