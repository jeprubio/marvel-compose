package com.rumosoft.characters.domain.usecase

import com.rumosoft.characters.domain.usecase.interfaces.SearchRepository
import com.rumosoft.characters.infrastructure.sampleData.SampleData
import com.rumosoft.commons.infrastructure.Resource
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

@ExperimentalCoroutinesApi
@ExtendWith(TestCoroutineExtension::class)
internal class SearchUseCaseImplTest {
    @MockK
    val repo: SearchRepository = mockk()
    private val useCase: SearchUseCase = SearchUseCaseImpl(repo)

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
            Resource.Success(SampleData.heroesSample)
    }

    private suspend fun `when the use case gets invoked`() {
        useCase.invoke("", 1)
    }

    private fun `then performSearch gets executed on repo`() {
        coVerify { repo.performSearch("", 1) }
    }
}
