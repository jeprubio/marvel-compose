package com.rumosoft.comics.presentation.component

import com.rumosoft.comics.domain.model.Comic
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class ComicSharedElementKeyTest {
    @Test
    fun `imageSharedKey uses comic id`() {
        val comic = Comic(
            id = 42,
            digitalId = 0,
            title = "Title",
            description = "",
            pageCount = 0,
            urls = emptyList(),
        )

        assertEquals(ComicImageKey(comicId = 42), comic.imageSharedKey())
    }

    @Test
    fun `imageSharedKey differs per comic id`() {
        val first = Comic(1, 0, "A", "", 0, emptyList())
        val second = Comic(2, 0, "B", "", 0, emptyList())

        assertEquals(ComicImageKey(1), first.imageSharedKey())
        assertEquals(ComicImageKey(2), second.imageSharedKey())
    }
}
