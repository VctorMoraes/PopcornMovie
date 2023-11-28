package com.victor.data.genres.datasource.remote

import com.victor.core.common.result.Result
import com.victor.core.network.BuildConfig
import com.victor.core.network.errorhandler.NetworkErrorHandler
import com.victor.data.genres.datasource.remote.api.GenreApi
import com.victor.data.genres.datasource.remote.dto.GenreDTO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GenreRemoteDataSource @Inject constructor(
    private val genreApi: GenreApi,
    private val errorHandler: NetworkErrorHandler
) {
    suspend fun getGenres(): Flow<Result<GenreDTO>> = flow {
        try {
            emit(Result.Success(genreApi.getGenres(BuildConfig.MOVIEDB_API_KEY)))
        } catch (throwable: Throwable) {
            Result.Error(errorHandler.getError(throwable))
        }
    }
}