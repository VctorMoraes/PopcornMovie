package com.victor.data.movies.mapper


import com.victor.core.model.MovieModel
import com.victor.data.movies.datasource.remote.dto.MovieItemDTO

fun MovieItemDTO.toModel(): MovieModel {
    return MovieModel(
        genreIds = this.genreIds,
        id = this.id,
        originalLanguage = this.originalLanguage,
        originalTitle = this.originalTitle,
        overview = this.overview,
        posterPath = this.posterPath,
        releaseDate = this.releaseDate,
        title = this.title,
        voteCount = this.voteCount
    )
}

fun MovieModel.toDomain(): MovieItemDTO {
    return MovieItemDTO(
        genreIds = this.genreIds,
        id = this.id,
        originalLanguage = this.originalLanguage,
        originalTitle = this.originalTitle,
        overview = this.overview,
        posterPath = this.posterPath,
        releaseDate = this.releaseDate,
        title = this.title,
        voteCount = this.voteCount
    )
}

