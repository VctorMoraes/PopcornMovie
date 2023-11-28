package com.victor.popcornmovie.view.main.viewmodel

import com.victor.core.model.GenreModel

data class HomeUiState(
    val selectedGenreName: String = "",
    val genreList: List<GenreModel> = listOf()
)