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
internal class GetComicsUseCaseTest {
    @MockK
    val repo: ComicsRepository = mockk()
    private val useCase: GetComicsUseCase = GetComicsUseCase(repo)

    @MockK
    val comicsList: List<Comic> = mockk()

    init {
        MockKAnnotations.init(this)
    }

    @Test
    fun `useCase should call repo`() {
        runTest {
            `given getComics invocation returns results`()

            `when the use case gets invoked`()

            `then getComics gets executed on repo`()
        }
    }

    private fun `given getComics invocation returns results`() {
        coEvery { repo.getComics(1) } returns
            Result.success(comicsList)
    }

    private suspend fun `when the use case gets invoked`() {
        useCase.invoke(1)
    }

    private fun `then getComics gets executed on repo`() {
        coVerify { repo.getComics(1) }
    }
}
