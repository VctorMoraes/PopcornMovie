package com.victor.domain.genres

import com.victor.core.common.result.Result
import com.victor.core.model.GenreModel
import com.victor.data.genres.repository.GenresRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetGenresUseCase @Inject constructor(
    private val genresRepository: GenresRepository
) {
    operator fun invoke(): Flow<Result<List<GenreModel>>> =
        genresRepository.getGenres()

}