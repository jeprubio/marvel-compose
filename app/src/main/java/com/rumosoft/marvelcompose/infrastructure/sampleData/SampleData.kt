package com.rumosoft.marvelcompose.infrastructure.sampleData

import com.rumosoft.marvelcompose.domain.model.Hero

object SampleData {
    val batman = Hero(
        name = "Batman"
    )

    val peopleSample = listOf(batman, batman.copy(name = "Spiderman"))
}
