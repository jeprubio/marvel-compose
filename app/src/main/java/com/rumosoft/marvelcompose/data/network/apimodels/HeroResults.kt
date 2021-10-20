package com.rumosoft.marvelcompose.data.network.apimodels

data class HeroResults(
    val code: Int? = null,
    val status: String? = null,
    val copyright: String? = null,
    val attributionText: String? = null,
    val attributionHTML: String? = null,
    val data: SearchData? = null,
)

data class SearchData(
    val offset: Int? = null,
    val limit: Int? = null,
    val total: Int? = null,
    val count: Int? = null,
    val results: List<HeroDto>? = null,
)
