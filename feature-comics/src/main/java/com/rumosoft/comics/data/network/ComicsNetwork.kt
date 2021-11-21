package com.rumosoft.comics.data.network

import com.rumosoft.commons.infrastructure.Resource

interface ComicsNetwork {
    suspend fun searchComics(offset: Int, limit: Int, nameStartsWith: String): Resource<ComicsResult>
}
