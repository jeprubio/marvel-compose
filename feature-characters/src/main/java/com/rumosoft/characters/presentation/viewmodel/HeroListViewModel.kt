package com.rumosoft.characters.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rumosoft.characters.domain.usecase.SearchUseCase
import com.rumosoft.characters.presentation.viewmodel.state.HeroListScreenState
import com.rumosoft.characters.presentation.viewmodel.state.HeroListState
import com.rumosoft.commons.domain.model.Character
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
        MutableStateFlow(HeroListScreenState(HeroListState.Loading))
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
                _heroListScreenState.update { it.copy(textSearched = searching.trim()) }
                performSearch(searching.trim(), fromStart = true)
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.Eagerly,
                initialValue = "",
            )
    }

    private fun performSearch(query: String = "", fromStart: Boolean) {
        try {
            Timber.d("Searching: $query")
            if (fromStart) {
                currentPage = 1
            }
            viewModelScope.launch {
                searchUseCase(query, currentPage).fold(
                    onSuccess = { charactersList ->
                        currentPage++
                        parseSuccessResponse(charactersList, currentPage)
                    },
                    onFailure = { parseErrorResponse(it) },
                )
            }
        } catch (exception: CancellationException) {
            throw exception
        } catch (exception: Exception) {
            Timber.e(exception, "Error performing hero search: $exception")
            parseErrorResponse(exception)
        }
    }

    private fun parseSuccessResponse(charactersList: List<Character>, page: Int) {
        setLoadingMore(false)
        _heroListScreenState.update {
            val previousList: List<Character> =
                if (page > 0 && it.heroListState is HeroListState.Success) {
                    it.heroListState.characters.orEmpty()
                } else {
                    emptyList()
                }
            _heroListScreenState.value
                .copy(
                    heroListState = HeroListState.Success(
                        previousList + charactersList,
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

    private fun parseErrorResponse(throwable: Throwable) {
        _heroListScreenState.update {
            it.copy(heroListState = HeroListState.Error(throwable, ::retry))
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
}
