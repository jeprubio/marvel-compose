package com.rumosoft.feature_characters.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rumosoft.commons.infrastructure.Resource
import com.rumosoft.commons.infrastructure.extensions.update
import com.rumosoft.commons.domain.model.Hero
import com.rumosoft.feature_characters.domain.usecase.GetComicThumbnailUseCase
import com.rumosoft.feature_characters.presentation.viewmodel.state.DetailsState
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

    fun setHero(hero: Hero) {
        viewModelScope.launch {
            _detailsState.emit(DetailsState.Success(hero))
            loadComicThumbnails(hero)
        }
    }

    private suspend fun loadComicThumbnails(hero: Hero) {
        hero.comics?.filter { it.thumbnail.isNullOrEmpty() }?.forEachIndexed { index, comic ->
            val comicId = comic.url.split("/").last().toInt()
            when (val response = getComicThumbnailUseCase(comicId)) {
                is Resource.Success -> {
                    val currentHero = (_detailsState.value as DetailsState.Success).hero
                    val updatedComics = currentHero.comics?.update(
                        index = index,
                        item = comic.copy(thumbnail = response.data)
                    )
                    val updatedHero = hero.copy(comics = updatedComics)
                    _detailsState.emit(DetailsState.Success(updatedHero))
                }
            }
        }
    }

    private fun initialDetailsState(): DetailsState = DetailsState.Loading
}
