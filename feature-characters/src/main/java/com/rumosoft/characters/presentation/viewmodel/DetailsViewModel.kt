package com.rumosoft.characters.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rumosoft.characters.domain.model.Character
import com.rumosoft.characters.domain.usecase.GetCharacterDetailsUseCase
import com.rumosoft.characters.domain.usecase.GetComicThumbnailUseCase
import com.rumosoft.characters.presentation.viewmodel.state.DetailsState
import com.rumosoft.marvelapi.infrastructure.extensions.update
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val getComicThumbnailUseCase: GetComicThumbnailUseCase,
    private val getCharacterDetailsUseCase: GetCharacterDetailsUseCase,
) : ViewModel() {
    val detailsState: StateFlow<DetailsState> get() = _detailsState
    private val _detailsState =
        MutableStateFlow(initialDetailsState())

    private var initialized = false
    private var characterId: Long = -1

    fun initialize(characterId: Long) {
        if (!initialized) {
            initialized = true
            this.characterId = characterId
            setCharacter(characterId)
        }
    }

    fun retry() {
        _detailsState.update { DetailsState.Loading }
        setCharacter(characterId)
    }

    private fun setCharacter(characterId: Long) {
        viewModelScope.launch {
            Timber.d("characterId: $characterId")
            getCharacterDetailsUseCase(characterId).fold(
                onSuccess = { character ->
                    if (character != null) {
                        _detailsState.update { DetailsState.Success(character) }
                        loadComicThumbnails(character)
                    } else {
                        _detailsState.update { DetailsState.Error(Exception("Character not found")) }
                    }
                },
                onFailure = { throwable ->
                    _detailsState.update { DetailsState.Error(throwable) }
                },
            )
        }
    }

    private suspend fun loadComicThumbnails(character: Character) {
        character.comics.filter { it.thumbnail.isNullOrEmpty() }.forEachIndexed { index, comic ->
            val comicId = comic.url.split("/").lastOrNull()?.toIntOrNull() ?: return@forEachIndexed
            getComicThumbnailUseCase(comicId).fold(
                onSuccess = { thumb ->
                    _detailsState.update { currentState ->
                        val currentHero = (currentState as? DetailsState.Success)?.character
                            ?: return@update currentState
                        val updatedComics = currentHero.comics.update(
                            index = index,
                            item = comic.copy(thumbnail = thumb),
                        )
                        DetailsState.Success(currentHero.copy(comics = updatedComics))
                    }
                },
                onFailure = {
                    /* Do nothing */
                },
            )
        }
    }

    private fun initialDetailsState(): DetailsState = DetailsState.Loading
}
