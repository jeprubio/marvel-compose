package com.rumosoft.comics.presentation.viewmodel.state

import com.rumosoft.comics.domain.model.Comic

const val ComicDetailsProgressIndicator = "comicDetailsProgressIndicator"
const val ComicDetailsErrorResult = "comicDetailsErrorResult"
const val ComicDetailsSuccessResult = "comicDetailsSuccessResult"

sealed class ComicDetailsState {
    object Loading : ComicDetailsState()

    class Error(
        val throwable: Throwable,
        val retry: () -> Unit = {},
    ) : ComicDetailsState()

    class Success(val comic: Comic) : ComicDetailsState()
}
