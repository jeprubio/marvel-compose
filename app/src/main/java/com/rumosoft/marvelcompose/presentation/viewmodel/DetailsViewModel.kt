package com.rumosoft.marvelcompose.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rumosoft.marvelcompose.domain.model.Hero
import com.rumosoft.marvelcompose.domain.model.Resource
import com.rumosoft.marvelcompose.domain.usecase.GetComicThumbnailUseCase
import com.rumosoft.marvelcompose.infrastructure.extensions.update
import com.rumosoft.marvelcompose.presentation.viewmodel.state.DetailsState
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
        hero.comics?.filter { it.thumbnail.isNullOrEmpty() }?.forEach { comic ->
            val comicId = comic.url.split("/").last().toInt()
            when (val response = getComicThumbnailUseCase(comicId)) {
                is Resource.Success -> {
                    val currentHero = (_detailsState.value as DetailsState.Success).hero
                    val updatedComics = currentHero.comics?.update(
                        index = hero.comics.indexOf(comic),
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
