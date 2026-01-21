package com.rumosoft.comics.presentation.viewmodel.state

import com.rumosoft.comics.domain.model.Comic

const val ComicDetailsProgressIndicator = "comicDetailsProgressIndicator"
const val ComicDetailsErrorResult = "comicDetailsErrorResult"
const val ComicDetailsSuccessResult = "comicDetailsSuccessResult"

sealed class ComicDetailsState {
    object Loading : ComicDetailsState()

    data class Error(
        val throwable: Throwable,
    ) : ComicDetailsState()

    data class Success(val comic: Comic) : ComicDetailsState()
}
