package com.rumosoft.comics.screen

import com.rumosoft.comics.domain.model.Comic

object FakeComics {
    fun getSampleComics(numComics: Int = 5): List<Comic> =
        (1..numComics).map { comicId ->
            Comic(
                id = comicId,
                title = "comic $comicId",
                description = "description $comicId",
                digitalId = comicId,
                pageCount = 1,
                thumbnail = "",
                urls = listOf(),
                characters = listOf(),
            )
        }
}
