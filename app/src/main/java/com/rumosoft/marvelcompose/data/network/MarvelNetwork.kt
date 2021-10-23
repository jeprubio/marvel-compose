package com.rumosoft.marvelcompose.data.network

import com.rumosoft.marvelcompose.domain.model.Hero
import com.rumosoft.marvelcompose.domain.model.Resource

interface MarvelNetwork {
    suspend fun searchHeroes(): Resource<List<Hero>>

    suspend fun getComicThumbnail(comicId: Int): Resource<String>
}
