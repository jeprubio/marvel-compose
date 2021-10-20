package com.rumosoft.marvelcompose.infrastructure.sampleData

import com.rumosoft.marvelcompose.domain.model.Hero

object SampleData {
    val batman = Hero(
        name = "Batman",
        description = "whatever",
        thumbnail = "",
    )

    val heroesSample = listOf(batman, batman.copy(name = "Spiderman"))
}
