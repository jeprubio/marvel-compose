package com.rumosoft.marvelcompose.data.repository

import com.rumosoft.marvelcompose.data.network.MarvelNetwork
import com.rumosoft.marvelcompose.domain.model.Hero
import com.rumosoft.marvelcompose.domain.model.Resource
import com.rumosoft.marvelcompose.domain.usecase.interfaces.SearchRepository
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val network: MarvelNetwork,
) : SearchRepository {
    override suspend fun performSearch(): Resource<List<Hero>?> {
        return network.searchHeroes()
    }

    override suspend fun getThumbnail(comicId: Int): Resource<String> {
        return network.getComicThumbnail(comicId)
    }
}
