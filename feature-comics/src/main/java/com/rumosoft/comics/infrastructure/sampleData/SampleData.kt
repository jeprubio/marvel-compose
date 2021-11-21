package com.rumosoft.comics.infrastructure.sampleData

import com.rumosoft.commons.domain.model.Comic

object SampleData {
    val comicsSample = (1..10).map {
        Comic(
            name = "comic $it",
            url = "url/comic/$it",
            thumbnail = "",
        )
    }
}
