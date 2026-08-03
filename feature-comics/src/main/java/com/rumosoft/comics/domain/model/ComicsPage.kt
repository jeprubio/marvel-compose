package com.rumosoft.comics.domain.model

data class ComicsPage(
    val comics: List<Comic>,
    val hasMorePages: Boolean,
)
