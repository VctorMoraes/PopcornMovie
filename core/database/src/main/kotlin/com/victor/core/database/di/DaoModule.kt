package com.victor.core.database.di

import com.victor.core.database.PopcornMovieDatabase
import com.victor.core.database.dao.GenreDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DaoModule {

    @Provides
    fun providesGenreDao(
        database: PopcornMovieDatabase,
    ): GenreDao = database.genreDao()
}