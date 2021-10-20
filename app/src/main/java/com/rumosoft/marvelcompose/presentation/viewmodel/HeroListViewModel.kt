package com.rumosoft.marvelcompose.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rumosoft.marvelcompose.domain.model.Resource
import com.rumosoft.marvelcompose.domain.usecase.SearchUseCase
import com.rumosoft.marvelcompose.presentation.viewmodel.state.HeroListResult
import com.rumosoft.marvelcompose.presentation.viewmodel.state.HeroListScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HeroListViewModel @Inject constructor(
    private val searchUseCase: SearchUseCase,
) : ViewModel() {

    val heroListScreenState: StateFlow<HeroListScreenState> get() = _heroListScreenState
    private val _heroListScreenState =
        MutableStateFlow(initialScreenState())

    init {
        performSearch()
    }

    private fun performSearch() {
        Timber.d("Searching:")
        viewModelScope.launch {
            val result = searchUseCase()
            Timber.d("Search result: $result")
            when (result) {
                is Resource.Success -> {
                    _heroListScreenState.emit(
                        _heroListScreenState.value
                            .copy(heroListResult = HeroListResult.Success(result.data))
                    )
                }
                is Resource.Error -> {
                    _heroListScreenState.emit(
                        _heroListScreenState.value
                            .copy(heroListResult = HeroListResult.Error(result.throwable) {})
                    )
                }
            }
        }
    }

    private fun initialScreenState(): HeroListScreenState =
        HeroListScreenState(HeroListResult.Loading)
}
