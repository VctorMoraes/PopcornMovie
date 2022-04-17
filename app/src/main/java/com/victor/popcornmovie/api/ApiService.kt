package com.victor.popcornmovie.api

import com.victor.popcornmovie.response.GenreResponse
import com.victor.popcornmovie.response.PopularResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): Response<PopularResponse>

    @GET("discover/movie")
    suspend fun getMoviesByGenre(
        @Query("api_key") apiKey: String,
        @Query("with_genres") genreId: Int,
        @Query("page") page: Int
    ): Response<PopularResponse>

    @GET("genre/movie/list")
    suspend fun getGenres(@Query("api_key") apiKey: String): Response<GenreResponse>
}