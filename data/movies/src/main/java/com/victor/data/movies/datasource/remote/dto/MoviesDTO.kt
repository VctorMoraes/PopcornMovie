package com.victor.data.movies.datasource.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MoviesDTO(
    var page: Int,
    var results: Collection<MovieItemDTO>,
    @SerialName("total_results") var totalResults: Int,
    @SerialName("total_pages") var totalPages: Int
)

@Serializable
data class MovieItemDTO(
    @SerialName("genre_ids") var genreIds: ArrayList<Int>,
    var id: Int,
    @SerialName("original_language") var originalLanguage: String,
    @SerialName("original_title") var originalTitle: String,
    var overview: String,
    @SerialName("poster_path") var posterPath: String?,
    @SerialName("release_date") var releaseDate: String,
    var title: String,
    @SerialName("vote_count") var voteCount: Int
)