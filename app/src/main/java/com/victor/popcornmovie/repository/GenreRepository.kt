package com.victor.popcornmovie.repository

import com.victor.popcornmovie.api.ApiService
import com.victor.popcornmovie.local.dao.GenreDao
import com.victor.popcornmovie.local.entities.Genre
import com.victor.popcornmovie.response.GenreResponse
import javax.inject.Inject

class GenreRepository @Inject constructor(
    private val apiService: ApiService,
    private val genreDao: GenreDao
) {

    suspend fun getGenres(): Result<List<Genre>> {
        val response = apiService.getGenres(API_KEY)
        return when {
            response.isSuccessful -> {
                response.body()?.let { genreResponse ->
                    insertGenre(genreResponse)
                    Result.success(getLocalGenres())
                } ?: Result.failure(Exception())
            }
            else -> Result.failure(Throwable(response.errorBody().toString()))
        }
    }

    private fun insertGenre(genreResponse: GenreResponse?) {
        genreResponse?.let { response ->

            val list = response.genres.map {
                Genre(it.id, it.name)
            }.toTypedArray()

            genreDao.insertAll(*list)
        }
    }

    fun getLocalGenres() = genreDao.getAll()

    companion object {
        const val API_KEY = "18b08f416f0563a8c496ac44f8989f85"
    }
}
