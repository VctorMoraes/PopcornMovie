package com.victor.data.movies.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.victor.core.model.MovieModel
import com.victor.data.movies.datasource.remote.MoviePagingSource
import com.victor.data.movies.datasource.remote.api.MovieApi
import com.victor.data.movies.mapper.toModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private val movieApi: MovieApi
) : MoviesRepository {

    override fun getPopularMovies(): Flow<PagingData<MovieModel>> = getMovies()

    override fun getMoviesByGenre(genreId: Int): Flow<PagingData<MovieModel>> = getMovies(genreId)

    private fun getMovies(genreId: Int? = null): Flow<PagingData<MovieModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { MoviePagingSource(movieApi, genreId) },
        ).flow.map { pagingData ->
            pagingData.map {
                it.toModel()
            }
        }
    }
}