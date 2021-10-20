package com.rumosoft.marvelcompose.presentation.viewmodel

import com.rumosoft.marvelcompose.MainCoroutineRule
import com.rumosoft.marvelcompose.domain.model.Resource
import com.rumosoft.marvelcompose.domain.usecase.SearchUseCase
import com.rumosoft.marvelcompose.infrastructure.sampleData.SampleData
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
internal class HeroListViewModelTest : TestCase() {
    @get:Rule
    val coroutineRule = MainCoroutineRule(TestCoroutineDispatcher())

    private val searchUseCase: SearchUseCase = mockk()
    private lateinit var sut: HeroListViewModel

    @Test
    fun `performSearch() calls searchUseCase`() {
        coroutineRule.testDispatcher.runBlockingTest {
            `given searchUseCase invocation returns results`()

            `when initialising the ViewModel`()

            `then searchUseCase gets invoked`()
        }
    }

    private fun `given searchUseCase invocation returns results`() {
        coEvery { searchUseCase.invoke() } returns
            Resource.Success(SampleData.peopleSample)
    }

    private fun `when initialising the ViewModel`() {
        sut = HeroListViewModel(searchUseCase)
    }

    private fun `then searchUseCase gets invoked`() {
        coVerify { searchUseCase.invoke() }
    }
}
