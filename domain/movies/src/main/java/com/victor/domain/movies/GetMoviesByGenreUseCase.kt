package com.victor.domain.movies

import com.victor.data.movies.repository.MoviesRepository
import javax.inject.Inject

class GetMoviesByGenreUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository
) {
    operator fun invoke(genreId: Int) =
        moviesRepository.getMoviesByGenre(genreId)
}