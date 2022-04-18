package com.rumosoft.characters.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rumosoft.characters.domain.usecase.GetComicThumbnailUseCase
import com.rumosoft.characters.presentation.viewmodel.state.DetailsState
import com.rumosoft.commons.domain.model.Character
import com.rumosoft.commons.infrastructure.Resource
import com.rumosoft.commons.infrastructure.extensions.update
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val getComicThumbnailUseCase: GetComicThumbnailUseCase,
) : ViewModel() {

    val detailsState: StateFlow<DetailsState> get() = _detailsState
    private val _detailsState =
        MutableStateFlow(initialDetailsState())

    fun setHero(character: Character) {
        viewModelScope.launch {
            _detailsState.emit(DetailsState.Success(character))
            loadComicThumbnails(character)
        }
    }

    private suspend fun loadComicThumbnails(character: Character) {
        character.comics?.filter { it.thumbnail.isNullOrEmpty() }?.forEachIndexed { index, comic ->
            val comicId = comic.url.split("/").last().toInt()
            when (val response = getComicThumbnailUseCase(comicId)) {
                is Resource.Success -> {
                    val currentHero = (_detailsState.value as DetailsState.Success).character
                    val updatedComics = currentHero.comics?.update(
                        index = index,
                        item = comic.copy(thumbnail = response.data)
                    )
                    val updatedHero = character.copy(comics = updatedComics)
                    _detailsState.emit(DetailsState.Success(updatedHero))
                }
            }
        }
    }

    private fun initialDetailsState(): DetailsState = DetailsState.Loading
}
