package com.victor.domain.movies

import androidx.paging.PagingData
import com.victor.core.model.MovieModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

//Todo: Create Interface
class GetMoviesUseCase @Inject constructor(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val getMoviesByGenreUseCase: GetMoviesByGenreUseCase,
) {
    operator fun invoke(genreId: Int?): Flow<PagingData<MovieModel>> {
        return when (genreId) {
            null,
            0 -> getPopularMoviesUseCase()

            else -> getMoviesByGenreUseCase(genreId)
        }
    }
}