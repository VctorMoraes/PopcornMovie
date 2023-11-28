package com.victor.data.genres.datasource.remote.api

import com.victor.data.genres.datasource.remote.dto.GenreDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GenreApi {
    @GET("genre/movie/list")
    suspend fun getGenres(@Query("api_key") apiKey: String): GenreDTO
}