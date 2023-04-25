package com.rumosoft.comics.domain.usecase

import com.rumosoft.comics.domain.usecase.interfaces.ComicsRepository
import com.rumosoft.commons.domain.model.Comic
import com.rumosoft.libraryTests.TestCoroutineExtension
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(TestCoroutineExtension::class)
internal class GetComicsUseCaseImplTest {
    @MockK
    val repo: ComicsRepository = mockk()
    private val useCase: GetComicsUseCase = GetComicsUseCaseImpl(repo)

    @MockK
    val comicsList: List<Comic> = mockk()

    init {
        MockKAnnotations.init(this)
    }

    @Test
    fun `useCase should call repo`() {
        runTest {
            `given performSearch invocation returns results`()

            `when the use case gets invoked`()

            `then performSearch gets executed on repo`()
        }
    }

    private fun `given performSearch invocation returns results`() {
        coEvery { repo.performSearch("", 1) } returns
            Result.success(comicsList)
    }

    private suspend fun `when the use case gets invoked`() {
        useCase.invoke("", 1)
    }

    private fun `then performSearch gets executed on repo`() {
        coVerify { repo.performSearch("", 1) }
    }
}
