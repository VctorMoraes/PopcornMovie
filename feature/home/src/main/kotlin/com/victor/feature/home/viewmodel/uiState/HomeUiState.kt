package com.victor.feature.home.viewmodel.uiState

import com.victor.core.model.GenreModel

sealed interface HomeUiState {

    data class Success(
        val selectedGenreName: String = "",
        val genreList: List<GenreModel> = listOf()
    ) : HomeUiState

    data object Error : HomeUiState

    data object Loading : HomeUiState

}