package com.victor.data.movies.repository

import androidx.paging.PagingData
import com.victor.core.common.result.Result
import com.victor.core.model.MovieModel
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {

    fun getPopularMovies(): Flow<PagingData<MovieModel>>

    fun getMoviesByGenre(genreId: Int): Flow<PagingData<MovieModel>>
}