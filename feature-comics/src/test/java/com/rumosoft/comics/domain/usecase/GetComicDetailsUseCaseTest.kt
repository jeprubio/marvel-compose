package com.rumosoft.comics.domain.usecase

import com.rumosoft.comics.domain.model.Comic
import com.rumosoft.comics.domain.usecase.interfaces.ComicsRepository
import com.rumosoft.libraryTests.TestCoroutineExtension
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(TestCoroutineExtension::class)
internal class GetComicDetailsUseCaseTest {
    @MockK
    val repo: ComicsRepository = mockk()
    private val useCase: GetComicDetailsUseCase = GetComicDetailsUseCase(repo)
    private val comicId = 123

    @MockK
    val comic: Comic = mockk()

    init {
        MockKAnnotations.init(this)
    }

    @Test
    fun `useCase should call repo`() {
        runTest {
            `given getDetails invocation returns results`()

            `when the use case gets invoked`()

            `then getDetails gets executed on repo`()
        }
    }

    private fun `given getDetails invocation returns results`() {
        coEvery { repo.getDetails(comicId) } returns
            Result.success(comic)
    }

    private suspend fun `when the use case gets invoked`() {
        useCase.invoke(comicId)
    }

    private fun `then getDetails gets executed on repo`() {
        coVerify { repo.getDetails(comicId) }
    }
}
