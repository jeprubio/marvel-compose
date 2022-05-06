package com.rumosoft.characters.domain.usecase

import com.rumosoft.commons.infrastructure.Resource
import com.rumosoft.characters.domain.usecase.interfaces.SearchRepository
import com.rumosoft.library_tests.TestCoroutineExtension
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExperimentalCoroutinesApi
@ExtendWith(TestCoroutineExtension::class)
internal class GetComicThumbnailUseCaseImplTest {
    @MockK
    val repo: SearchRepository = mockk()
    private val useCase: GetComicThumbnailUseCase = GetComicThumbnailUseCaseImpl(repo)
    private val comicId = 1
    private val thumbnailUrl = "thumbnailUrl"

    init {
        MockKAnnotations.init(this)
    }

    @Test
    fun `useCase should call repo`() {
        runTest {
            `given getThumbnail invocation returns results`()

            `when the use case gets invoked`()

            `then getThumbnail gets executed on repo`()
        }
    }

    private fun `given getThumbnail invocation returns results`() {
        coEvery { repo.getThumbnail(comicId) } returns
            Resource.Success(thumbnailUrl)
    }

    private suspend fun `when the use case gets invoked`() {
        useCase.invoke(comicId)
    }

    private fun `then getThumbnail gets executed on repo`() {
        coVerify { repo.getThumbnail(comicId) }
    }
}
