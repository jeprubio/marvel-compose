package com.rumosoft.marvelcompose.data.network

import com.rumosoft.marvelcompose.domain.model.Resource

interface MarvelNetwork {
    suspend fun searchHeroes(offset: Int, limit: Int): Resource<HeroesResult>

    suspend fun getComicThumbnail(comicId: Int): Resource<String>
}
