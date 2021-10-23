package com.rumosoft.marvelcompose.domain.usecase

import com.rumosoft.marvelcompose.MainCoroutineRule
import com.rumosoft.marvelcompose.domain.model.Resource
import com.rumosoft.marvelcompose.domain.usecase.interfaces.SearchRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
internal class GetComicThumbnailUseCaseImplTest {
    @get:Rule
    val coroutineRule = MainCoroutineRule(TestCoroutineDispatcher())

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
        coroutineRule.testDispatcher.runBlockingTest {
            `given getThumbnail invocation returns results`()

            `when the use case gets invoked`()

            `then performSearch gets executed on repo`()
        }
    }

    private fun `given getThumbnail invocation returns results`() {
        coEvery { repo.getThumbnail(comicId) } returns
            Resource.Success(thumbnailUrl)
    }

    private suspend fun `when the use case gets invoked`() {
        useCase.invoke(comicId)
    }

    private fun `then performSearch gets executed on repo`() {
        coVerify { repo.getThumbnail(comicId) }
    }
}
