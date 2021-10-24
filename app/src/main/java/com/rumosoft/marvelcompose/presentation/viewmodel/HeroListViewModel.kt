package com.rumosoft.marvelcompose.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rumosoft.marvelcompose.domain.model.Hero
import com.rumosoft.marvelcompose.domain.model.Resource
import com.rumosoft.marvelcompose.domain.usecase.SearchUseCase
import com.rumosoft.marvelcompose.presentation.viewmodel.state.HeroListScreenState
import com.rumosoft.marvelcompose.presentation.viewmodel.state.HeroListState
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

    private var currentHeroes: List<Hero> = emptyList()

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
                    parseSuccessResponse(result)
                }
                is Resource.Error -> {
                    _heroListScreenState.emit(
                        _heroListScreenState.value
                            .copy(heroListState = HeroListState.Error(result.throwable) {})
                    )
                }
            }
        }
    }

    internal fun heroClicked(hero: Hero) {
        Timber.d("On hero clicked: $hero")
        viewModelScope.launch {
            _heroListScreenState.emit(
                _heroListScreenState.value
                    .copy(selectedHero = hero)
            )
        }
    }

    internal fun resetSelectedHero() {
        Timber.d("reset selected person")
        viewModelScope.launch {
            _heroListScreenState.emit(
                _heroListScreenState.value
                    .copy(selectedHero = null)
            )
        }
    }

    private suspend fun parseSuccessResponse(result: Resource.Success<List<Hero>?>) {
        val newList = currentHeroes.toMutableList()
        result.data?.let { newList.addAll(it) }
        currentHeroes = newList.toList()
        _heroListScreenState.emit(
            _heroListScreenState.value
                .copy(heroListState = HeroListState.Success(newList, ::heroClicked, ::onReachedEnd))
        )
    }

    private fun onReachedEnd() {
        performSearch()
    }

    private fun initialScreenState(): HeroListScreenState =
        HeroListScreenState(HeroListState.Loading)
}
