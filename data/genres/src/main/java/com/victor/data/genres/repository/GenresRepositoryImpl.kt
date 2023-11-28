package com.victor.data.genres.repository


import com.victor.core.common.result.Result
import com.victor.core.common.result.fold
import com.victor.core.database.dao.GenreDao
import com.victor.core.model.GenreModel
import com.victor.data.genres.datasource.remote.GenreRemoteDataSource
import com.victor.data.genres.datasource.remote.dto.GenreDTO
import com.victor.data.genres.mapper.toEntity
import com.victor.data.genres.mapper.toModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GenresRepositoryImpl @Inject constructor(
    private val genreRemoteDataSource: GenreRemoteDataSource,
    private val genreDao: GenreDao
) : GenresRepository {

    override fun getGenres(): Flow<Result<List<GenreModel>>> = flow {
        genreRemoteDataSource.getGenres().collect {
            it.fold({ genreDto ->
                insertGenre(genreDto)
                emit(Result.Success(getLocalGenres()))
            }, { error ->
                emit(Result.Error(error))
            })
        }
    }

    private fun insertGenre(genreDTO: GenreDTO?) {
        genreDTO?.let { response ->
            val list = response.genres.map {
                it.toEntity()
            }.toTypedArray()

            genreDao.insertAll(*list)
        }
    }

    private fun getLocalGenres(): List<GenreModel> {
        return genreDao.getAll().map {
            it.toModel()
        }
    }
}
