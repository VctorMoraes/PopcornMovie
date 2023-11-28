package com.victor.data.movies.datasource.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.victor.core.network.BuildConfig
import com.victor.data.movies.datasource.remote.api.MovieApi
import com.victor.data.movies.datasource.remote.dto.MovieItemDTO
import com.victor.data.movies.datasource.remote.dto.MoviesDTO
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class MoviePagingSource @Inject constructor(
    private val movieApi: MovieApi,
    private val genreId: Int? = null
) : PagingSource<Int, MovieItemDTO>() {

    private suspend fun getMovies(page: Int): MoviesDTO {
        return genreId?.let {
            movieApi.getMoviesByGenre(BuildConfig.MOVIEDB_API_KEY, genreId, page)
        } ?: movieApi.getPopularMovies(BuildConfig.MOVIEDB_API_KEY, page)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieItemDTO> {
        val page = params.key ?: MOVIES_FIRST_PAGE_INDEX

        return try {
            val response = getMovies(page)
            val movies = response.results.toList()
            val nextKey = if (movies.isEmpty()) {
                null
            } else {
                page + 1
            }
            LoadResult.Page(
                data = movies,
                prevKey = if (page == MOVIES_FIRST_PAGE_INDEX) null else page - 1,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, MovieItemDTO>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    companion object {
        private const val API_KEY = "a5d8cfc590c00b843994d69b85c230c7"
        private const val MOVIES_FIRST_PAGE_INDEX = 1
    }
}