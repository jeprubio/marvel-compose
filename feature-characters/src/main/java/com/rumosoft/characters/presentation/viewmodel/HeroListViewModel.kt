package com.rumosoft.characters.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rumosoft.characters.domain.model.Character
import com.rumosoft.characters.domain.model.CharactersPage
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
        if (fromStart) {
            currentPage = 1
        }
        viewModelScope.launch {
            try {
                getCharactersUseCase(currentPage).fold(
                    onSuccess = { charactersPage ->
                        parseSuccessResponse(charactersPage, currentPage)
                        currentPage++
                    },
                    onFailure = { parseErrorResponse(it) },
                )
            } catch (exception: CancellationException) {
                throw exception
            } catch (exception: Exception) {
                Timber.e(exception, "Error loading characters: $exception")
                parseErrorResponse(exception)
            }
        }
    }

    private fun parseSuccessResponse(charactersPage: CharactersPage, page: Int) {
        setLoadingMore(false)
        _heroListScreenState.update {
            val previousList: List<Character> =
                if (page > 1 && it.heroListState is HeroListState.Success) {
                    it.heroListState.characters
                } else {
                    emptyList()
                }
            it.copy(
                heroListState = HeroListState.Success(
                    characters = previousList + charactersPage.characters,
                    loadingMore = false,
                    hasMorePages = charactersPage.hasMorePages,
                ),
            )
        }
    }

    internal fun characterClicked(character: Character) {
        Timber.d("On hero clicked: $character")
        _heroListScreenState.update { it.copy(selectedCharacter = character) }
    }

    fun resetSelectedCharacter() {
        Timber.d("Reset selected character")
        _heroListScreenState.update { it.copy(selectedCharacter = null) }
    }

    private fun parseErrorResponse(throwable: Throwable) {
        _heroListScreenState.update {
            it.copy(heroListState = HeroListState.Error(throwable))
        }
    }

    fun onReachedEnd() {
        val current =
            _heroListScreenState.value.heroListState as? HeroListState.Success ?: return
        if (!current.hasMorePages || current.loadingMore) return
        setLoadingMore(true)
        loadCharacters(fromStart = false)
    }

    fun retry() {
        _heroListScreenState.update { it.copy(heroListState = HeroListState.Loading) }
        loadCharacters(fromStart = false)
    }

    private fun setLoadingMore(value: Boolean) {
        _heroListScreenState.update { current ->
            val successState = current.heroListState as? HeroListState.Success
                ?: return@update current
            current.copy(heroListState = successState.copy(loadingMore = value))
        }
    }
}
