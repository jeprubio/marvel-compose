package com.rumosoft.characters.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rumosoft.characters.domain.usecase.SearchUseCase
import com.rumosoft.characters.presentation.viewmodel.state.HeroListScreenState
import com.rumosoft.characters.presentation.viewmodel.state.HeroListState
import com.rumosoft.commons.domain.model.Character
import com.rumosoft.commons.infrastructure.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HeroListViewModel @Inject constructor(
    private val searchUseCase: SearchUseCase,
) : ViewModel() {
    companion object {
        const val DEBOUNCE_DELAY = 1_000L
    }

    val heroListScreenState: StateFlow<HeroListScreenState> get() = _heroListScreenState
    private val _heroListScreenState =
        MutableStateFlow(initialScreenState())
    private val textSearched = MutableStateFlow("")
    private var currentPage = 1

    init {
        observeTextSearched()
    }

    fun onQueryChanged(query: String) {
        if (query != _heroListScreenState.value.textSearched) {
            textSearched.value = query
        }
    }

    fun onToggleSearchClick() {
        _heroListScreenState.update {
            it.copy(showingSearchBar = !_heroListScreenState.value.showingSearchBar)
        }
    }

    @OptIn(FlowPreview::class)
    private fun observeTextSearched() {
        textSearched
            .debounce(DEBOUNCE_DELAY)
            .onEach { searching ->
                _heroListScreenState.update { it.copy(textSearched = searching) }
                performSearch(searching, fromStart = true)
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.Eagerly,
                initialValue = "",
            )
    }

    private fun performSearch(query: String = "", fromStart: Boolean) {
        try {
            Timber.d("Searching: $query")
            currentPage = if (fromStart) 1 else currentPage + 1
            viewModelScope.launch {
                when (val result = searchUseCase(query, currentPage)) {
                    is Resource.Success -> {
                        parseSuccessResponse(result)
                    }
                    is Resource.Error -> {
                        parseErrorResponse(result)
                    }
                }
            }
        } catch (exception: Exception) {
            if (exception !is CancellationException) {
                // TODO Do something?
            }
        }
    }

    private fun parseSuccessResponse(result: Resource.Success<List<Character>?>) {
        setLoadingMore(false)
        _heroListScreenState.update {
            _heroListScreenState.value
                .copy(
                    heroListState = HeroListState.Success(
                        result.data,
                        false,
                        ::characterClicked,
                        ::onReachedEnd,
                    ),
                )
        }
    }

    internal fun characterClicked(character: Character) {
        Timber.d("On hero clicked: $character")
        viewModelScope.launch {
            _heroListScreenState.update {
                it.copy(selectedCharacter = character)
            }
        }
    }

    fun resetSelectedCharacter() {
        Timber.d("Reset selected character")
        viewModelScope.launch {
            _heroListScreenState.update {
                it.copy(selectedCharacter = null)
            }
        }
    }

    private fun parseErrorResponse(result: Resource.Error) {
        _heroListScreenState.update {
            it.copy(heroListState = HeroListState.Error(result.throwable, ::retry))
        }
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
        performSearch(_heroListScreenState.value.textSearched, fromStart = false)
    }

    private fun setLoadingMore(value: Boolean) {
        val currentHeroes =
            (_heroListScreenState.value.heroListState as? HeroListState.Success)?.characters
        if (currentHeroes != null) {
            _heroListScreenState.update {
                it.copy(
                    heroListState = HeroListState.Success(
                        characters = currentHeroes,
                        loadingMore = value,
                    ),
                )
            }
        }
    }

    private fun initialScreenState(): HeroListScreenState =
        HeroListScreenState(HeroListState.Loading)
}
