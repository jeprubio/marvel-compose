package com.rumosoft.characters.domain.usecase

import com.rumosoft.characters.domain.usecase.interfaces.CharactersRepository
import com.rumosoft.characters.infrastructure.sampleData.SampleData
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
internal class GetCharactersUseCaseTest {
    @MockK
    val repo: CharactersRepository = mockk()
    private val useCase: GetCharactersUseCase = GetCharactersUseCase(repo)

    init {
        MockKAnnotations.init(this)
    }

    @Test
    fun `useCase should call repo`() {
        runTest {
            `given getCharacters invocation returns results`()

            `when the use case gets invoked`()

            `then getCharacters gets executed on repo`()
        }
    }

    private fun `given getCharacters invocation returns results`() {
        coEvery { repo.getCharacters(1) } returns
            Result.success(SampleData.heroesSample)
    }

    private suspend fun `when the use case gets invoked`() {
        useCase.invoke(1)
    }

    private fun `then getCharacters gets executed on repo`() {
        coVerify { repo.getCharacters(1) }
    }
}
