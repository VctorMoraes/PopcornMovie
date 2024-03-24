package com.victor.feature.home.viewmodel.uiState

import com.victor.core.model.MovieModel

data class MovieDetails(
    val genreIds: ArrayList<Int>,
    val id: Int,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val posterPath: String?,
    val releaseDate: String,
    val title: String,
    val voteCount: Int
)

fun MovieModel.toSuccessUiState() =
    MovieDetails(
        genreIds = genreIds,
        id = id,
        originalLanguage = originalLanguage,
        originalTitle = originalTitle,
        overview = overview,
        posterPath = IMAGE_BASE_URL + posterPath,
        releaseDate = releaseDate,
        title = title,
        voteCount = voteCount
    )

const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500"