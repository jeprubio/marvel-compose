package com.rumosoft.feature_characters.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rumosoft.commons.domain.model.Character
import com.rumosoft.commons.infrastructure.Resource
import com.rumosoft.feature_characters.domain.usecase.SearchUseCase
import com.rumosoft.feature_characters.presentation.viewmodel.state.HeroListScreenState
import com.rumosoft.feature_characters.presentation.viewmodel.state.HeroListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

const val DEBOUNCE_DELAY = 1_000L

@HiltViewModel
class HeroListViewModel @Inject constructor(
    private val searchUseCase: SearchUseCase,
) : ViewModel() {

    val heroListScreenState: StateFlow<HeroListScreenState> get() = _heroListScreenState
    private val _heroListScreenState =
        MutableStateFlow(initialScreenState())

    private var performSearchJob: Job? = null

    init {
        performSearch(_heroListScreenState.value.textSearched, true)
    }

    fun onQueryChanged(query: String) {
        if (query != _heroListScreenState.value.textSearched) {
            Timber.d("Searching: $query")
            cancelJobIfActive()
            performSearchJob = viewModelScope.launch {
                _heroListScreenState.emit(
                    _heroListScreenState.value
                        .copy(textSearched = query, heroListState = HeroListState.Loading)
                )
                delay(DEBOUNCE_DELAY)
                try {
                    _heroListScreenState.value = _heroListScreenState.value.copy(textSearched = query)
                    performSearch(query, true)
                } catch (exception: Exception) {
                    if (exception !is CancellationException) {
                        // TODO Do something?
                    }
                }
            }
        }
    }

    fun onToggleSearchClick() {
        _heroListScreenState.value = heroListScreenState.value
            .copy(showingSearchBar = !_heroListScreenState.value.showingSearchBar)
    }

    private fun performSearch(query: String = "", fromStart: Boolean) {
        viewModelScope.launch {
            when (val result = searchUseCase(query, fromStart)) {
                is Resource.Success -> {
                    parseSuccessResponse(result)
                }
                is Resource.Error -> {
                    parseErrorResponse(result)
                }
            }
        }
    }

    internal fun characterClicked(character: Character) {
        Timber.d("On hero clicked: $character")
        viewModelScope.launch {
            _heroListScreenState.emit(
                _heroListScreenState.value
                    .copy(selectedCharacter = character)
            )
        }
    }

    fun resetSelectedCharacter() {
        Timber.d("Reset selected character")
        viewModelScope.launch {
            _heroListScreenState.emit(
                _heroListScreenState.value
                    .copy(selectedCharacter = null)
            )
        }
    }

    private suspend fun parseSuccessResponse(result: Resource.Success<List<Character>?>) {
        setLoadingMore(false)
        _heroListScreenState.emit(
            _heroListScreenState.value
                .copy(
                    heroListState = HeroListState.Success(
                        result.data,
                        false,
                        ::characterClicked,
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
        loadMoreData()
    }

    private fun retry() {
        loadMoreData()
    }

    private fun loadMoreData() {
        viewModelScope.launch {
            setLoadingMore(true)
        }
        performSearch(_heroListScreenState.value.textSearched, false)
    }

    private suspend fun setLoadingMore(value: Boolean) {
        val currentHeroes = (_heroListScreenState.value.heroListState as? HeroListState.Success)?.characters
        if (currentHeroes != null) {
            _heroListScreenState.emit(
                _heroListScreenState.value
                    .copy(
                        heroListState = HeroListState.Success(
                            characters = currentHeroes,
                            loadingMore = value,
                        )
                    )
            )
        }
    }

    private fun cancelJobIfActive() {
        performSearchJob?.takeIf { it.isActive }?.cancel()
    }

    private fun initialScreenState(): HeroListScreenState =
        HeroListScreenState(HeroListState.Loading)
}
