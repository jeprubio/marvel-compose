package com.rumosoft.feature_characters.domain.usecase

import com.rumosoft.commons.infrastructure.Resource
import com.rumosoft.feature_characters.CoroutineTest
import com.rumosoft.feature_characters.domain.usecase.interfaces.SearchRepository
import com.rumosoft.feature_characters.infrastructure.sampleData.SampleData
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
internal class SearchUseCaseImplTest : CoroutineTest {

    override var dispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()
    override var testScope: TestCoroutineScope = TestCoroutineScope(dispatcher)

    @MockK
    val repo: SearchRepository = mockk()

    private val useCase: SearchUseCase = SearchUseCaseImpl(repo)

    init {
        MockKAnnotations.init(this)
    }

    @Test
    fun `useCase should call repo`() {
        testScope.runBlockingTest {
            `given performSearch invocation returns results`()

            `when the use case gets invoked`()

            `then performSearch gets executed on repo`()
        }
    }

    private fun `given performSearch invocation returns results`() {
        coEvery { repo.performSearch("") } returns
            Resource.Success(SampleData.heroesSample)
    }

    private suspend fun `when the use case gets invoked`() {
        useCase.invoke("", false)
    }

    private fun `then performSearch gets executed on repo`() {
        coVerify { repo.performSearch("") }
    }
}
