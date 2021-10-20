package com.rumosoft.marvelcompose.data.repository

import com.rumosoft.marvelcompose.domain.model.Hero
import com.rumosoft.marvelcompose.domain.model.Resource
import com.rumosoft.marvelcompose.domain.usecase.interfaces.SearchRepository
import com.rumosoft.marvelcompose.infrastructure.sampleData.SampleData

class SearchRepositoryImpl : SearchRepository {
    override suspend fun performSearch(): Resource<List<Hero>?> {
        return Resource.Success(SampleData.peopleSample)
    }
}
