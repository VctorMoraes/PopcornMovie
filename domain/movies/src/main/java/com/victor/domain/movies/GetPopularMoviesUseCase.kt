package com.victor.domain.movies

import com.victor.data.movies.repository.MoviesRepository
import javax.inject.Inject

class GetPopularMoviesUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository
) {
    //Todo: Create interface
    operator fun invoke() =
        moviesRepository.getPopularMovies()

}