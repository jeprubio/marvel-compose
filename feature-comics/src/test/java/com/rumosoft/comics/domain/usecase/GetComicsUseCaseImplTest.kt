package com.rumosoft.comics.domain.usecase

import com.rumosoft.comics.MainCoroutineRule
import com.rumosoft.comics.domain.usecase.interfaces.ComicsRepository
import com.rumosoft.commons.domain.model.Comic
import com.rumosoft.commons.infrastructure.Resource
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
internal class GetComicsUseCaseImplTest {

    @get:Rule
    val coroutineRule = MainCoroutineRule(TestCoroutineDispatcher())

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
        coroutineRule.testDispatcher.runBlockingTest {
            `given performSearch invocation returns results`()

            `when the use case gets invoked`()

            `then performSearch gets executed on repo`()
        }
    }

    private fun `given performSearch invocation returns results`() {
        coEvery { repo.performSearch("") } returns
            Resource.Success(comicsList)
    }

    private suspend fun `when the use case gets invoked`() {
        useCase.invoke("", false)
    }

    private fun `then performSearch gets executed on repo`() {
        coVerify { repo.performSearch("") }
    }
}
