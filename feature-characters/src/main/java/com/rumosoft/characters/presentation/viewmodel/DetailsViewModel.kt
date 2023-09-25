package com.rumosoft.characters.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rumosoft.characters.domain.model.Character
import com.rumosoft.characters.domain.usecase.GetComicThumbnailUseCase
import com.rumosoft.characters.presentation.navigation.NavCharItem
import com.rumosoft.characters.presentation.viewmodel.state.DetailsState
import com.rumosoft.marvelapi.infrastructure.extensions.update
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getComicThumbnailUseCase: GetComicThumbnailUseCase,
) : ViewModel() {

    val character: Character = savedStateHandle[NavCharItem.Details.navArgs[0].name]!!
    val detailsState: StateFlow<DetailsState> get() = _detailsState
    private val _detailsState =
        MutableStateFlow(initialDetailsState())

    init {
        viewModelScope.launch {
            _detailsState.emit(DetailsState.Success(character))
            loadComicThumbnails(character)
        }
    }

    private suspend fun loadComicThumbnails(character: Character) {
        character.comics?.filter { it.thumbnail.isNullOrEmpty() }?.forEachIndexed { index, comic ->
            val comicId = comic.url.split("/").last().toInt()
            getComicThumbnailUseCase(comicId).fold(
                onSuccess = { thumb ->
                    val currentHero = (_detailsState.value as DetailsState.Success).character
                    val updatedComics = currentHero.comics?.update(
                        index = index,
                        item = comic.copy(thumbnail = thumb),
                    )
                    val updatedHero = character.copy(comics = updatedComics)
                    _detailsState.emit(DetailsState.Success(updatedHero))
                },
                onFailure = {
                    /* Do nothing */
                },
            )
        }
    }

    private fun initialDetailsState(): DetailsState = DetailsState.Loading
}
