package com.rumosoft.feature_characters.domain.usecase

import com.rumosoft.commons.infrastructure.Resource
import com.rumosoft.feature_characters.CoroutineTest
import com.rumosoft.feature_characters.domain.usecase.interfaces.SearchRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
internal class GetComicThumbnailUseCaseImplTest : CoroutineTest {

    override var dispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()
    override var testScope: TestCoroutineScope = TestCoroutineScope(dispatcher)

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
        testScope.runBlockingTest {
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
