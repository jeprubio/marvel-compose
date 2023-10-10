package com.rumosoft.marvelcompose

import com.rumosoft.characters.domain.model.Character
import com.rumosoft.characters.domain.usecase.interfaces.SearchRepository
import com.rumosoft.characters.infrastructure.di.RepositoryModule
import dagger.Binds
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Inject
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [RepositoryModule::class]
)
@Suppress("unused")
abstract class FakeRepositoryModule {
    @Singleton
    @Binds
    abstract fun bindRepositoryService(
        fakeSearchRepository: FakeSearchRepository
    ): SearchRepository
}

class FakeSearchRepository @Inject constructor() : SearchRepository {
    override suspend fun performSearch(nameStartsWith: String, page: Int): Result<List<Character>> {
        return Result.success(
            (0..10).map {
                Character(
                    name = "character $it",
                    description = "description $it",
                    thumbnail = "thumbnail $it",
                )
            }
        )
    }

    override suspend fun getThumbnail(comicId: Int): Result<String> {
        return Result.success("thumbnail $comicId")
    }
}
