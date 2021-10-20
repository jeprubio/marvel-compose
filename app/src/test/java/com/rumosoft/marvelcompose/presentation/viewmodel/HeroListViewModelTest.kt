package com.rumosoft.marvelcompose.presentation.viewmodel

import com.rumosoft.marvelcompose.MainCoroutineRule
import com.rumosoft.marvelcompose.domain.model.Resource
import com.rumosoft.marvelcompose.domain.usecase.SearchUseCase
import com.rumosoft.marvelcompose.infrastructure.sampleData.SampleData
import com.rumosoft.marvelcompose.presentation.viewmodel.state.HeroListResult
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
    private lateinit var heroListViewModel: HeroListViewModel

    @Test
    fun `performSearch() calls searchUseCase`() {
        coroutineRule.testDispatcher.runBlockingTest {
            `given searchUseCase invocation returns results`()

            `when initialising the ViewModel`()

            `then searchUseCase gets invoked`()
        }
    }

    @Test
    fun `If performSearch() goes well the HeroListScreenState will be Success`() =
        coroutineRule.testDispatcher.runBlockingTest {
            `given searchUseCase invocation returns results`()

            `when initialising the ViewModel`()

            `then HeroListScreenSuccess should be Success`()
        }

    @Test
    fun `If performSearch() returns error the HeroListScreenState will be Error`() =
        coroutineRule.testDispatcher.runBlockingTest {
            `given searchUseCase invocation returns error`()

            `when initialising the ViewModel`()

            `then HeroListScreenSuccess should be Error`()
        }

    private fun `given searchUseCase invocation returns results`() {
        coEvery { searchUseCase.invoke() } returns
            Resource.Success(SampleData.heroesSample)
    }

    private fun `given searchUseCase invocation returns error`() {
        coEvery { searchUseCase.invoke() } returns
            Resource.Error(Exception())
    }

    private fun `when initialising the ViewModel`() {
        heroListViewModel = HeroListViewModel(searchUseCase)
    }

    private fun `then searchUseCase gets invoked`() {
        coVerify { searchUseCase.invoke() }
    }

    private fun `then HeroListScreenSuccess should be Success`() {
        assertTrue(heroListViewModel.heroListScreenState.value.heroListResult is HeroListResult.Success)
    }

    private fun `then HeroListScreenSuccess should be Error`() {
        assertTrue(heroListViewModel.heroListScreenState.value.heroListResult is HeroListResult.Error)
    }
}
