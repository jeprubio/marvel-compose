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

    init {
        performSearch(true)
    }

    private fun performSearch(fromStart: Boolean) {
        viewModelScope.launch {
            when (val result = searchUseCase(fromStart)) {
                is Resource.Success -> {
                    parseSuccessResponse(result)
                }
                is Resource.Error -> {
                    parseErrorResponse(result)
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
        Timber.d("Reset selected hero")
        viewModelScope.launch {
            _heroListScreenState.emit(
                _heroListScreenState.value
                    .copy(selectedHero = null)
            )
        }
    }

    private suspend fun parseSuccessResponse(result: Resource.Success<List<Hero>?>) {
        setLoadingMore(false)
        _heroListScreenState.emit(
            _heroListScreenState.value
                .copy(
                    heroListState = HeroListState.Success(
                        result.data,
                        false,
                        ::heroClicked,
                        ::onReachedEnd
                    )
                )
        )
    }

    private suspend fun parseErrorResponse(result: Resource.Error) {
        _heroListScreenState.emit(
            _heroListScreenState.value
                .copy(heroListState = HeroListState.Error(result.throwable, ::retry))
        )
    }

    private fun onReachedEnd() {
        viewModelScope.launch {
            setLoadingMore(true)
        }
        performSearch(false)
    }

    private fun retry() {
        onReachedEnd()
    }

    private suspend fun setLoadingMore(value: Boolean) {
        val currentHeroes = (_heroListScreenState.value.heroListState as? HeroListState.Success)?.heroes
        if (currentHeroes != null) {
            _heroListScreenState.emit(
                _heroListScreenState.value
                    .copy(
                        heroListState = HeroListState.Success(
                            heroes = currentHeroes,
                            loadingMore = value,
                        )
                    )
            )
        }
    }

    private fun initialScreenState(): HeroListScreenState =
        HeroListScreenState(HeroListState.Loading)
}
