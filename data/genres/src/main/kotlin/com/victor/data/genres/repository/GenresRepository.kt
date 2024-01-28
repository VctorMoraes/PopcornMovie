package com.victor.data.genres.repository

import com.victor.core.common.result.Result
import com.victor.core.model.GenreModel
import kotlinx.coroutines.flow.Flow

interface GenresRepository {

    fun getGenres(): Flow<Result<List<GenreModel>>>

}