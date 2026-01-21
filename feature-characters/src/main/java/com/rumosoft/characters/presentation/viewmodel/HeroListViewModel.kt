package com.rumosoft.characters.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rumosoft.characters.domain.model.Character
import com.rumosoft.characters.domain.usecase.GetCharactersUseCase
import com.rumosoft.characters.presentation.viewmodel.state.HeroListScreenState
import com.rumosoft.characters.presentation.viewmodel.state.HeroListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HeroListViewModel @Inject constructor(
    private val getCharactersUseCase: GetCharactersUseCase,
) : ViewModel() {

    val heroListScreenState: StateFlow<HeroListScreenState> get() = _heroListScreenState
    private val _heroListScreenState =
        MutableStateFlow(HeroListScreenState(HeroListState.Loading))
    private var currentPage = 1

    init {
        loadCharacters()
    }

    private fun loadCharacters(fromStart: Boolean = true) {
        try {
            if (fromStart) {
                currentPage = 1
            }
            viewModelScope.launch {
                getCharactersUseCase(currentPage).fold(
                    onSuccess = { charactersList ->
                        parseSuccessResponse(charactersList, currentPage++)
                    },
                    onFailure = { parseErrorResponse(it) },
                )
            }
        } catch (exception: CancellationException) {
            throw exception
        } catch (exception: Exception) {
            Timber.e(exception, "Error loading characters: $exception")
            parseErrorResponse(exception)
        }
    }

    private fun parseSuccessResponse(charactersList: List<Character>, page: Int) {
        setLoadingMore(false)
        _heroListScreenState.update {
            val previousList: List<Character> =
                if (page > 1 && it.heroListState is HeroListState.Success) {
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
        loadCharacters(fromStart = false)
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
