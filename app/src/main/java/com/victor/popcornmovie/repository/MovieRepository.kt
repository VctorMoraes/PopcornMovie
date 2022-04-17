package com.victor.popcornmovie.repository

import com.victor.popcornmovie.api.ApiService

class MovieRepository(private val apiService: ApiService) {
    suspend fun getPopularMovies(page: Int) = apiService.getPopularMovies(API_KEY, page)

    suspend fun getMoviesByGenre(genreId: Int, page: Int) = apiService.getMoviesByGenre(API_KEY, genreId, page)

    companion object {
        const val API_KEY = "18b08f416f0563a8c496ac44f8989f85"
    }
}