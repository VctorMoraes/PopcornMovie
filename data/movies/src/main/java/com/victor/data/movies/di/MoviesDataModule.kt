package com.victor.data.movies.di

import com.victor.data.movies.datasource.remote.MoviePagingSource
import com.victor.data.movies.datasource.remote.api.MovieApi
import com.victor.data.movies.repository.MoviesRepository
import com.victor.data.movies.repository.MoviesRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface MoviesDataModule {

    @Binds
    fun bindsMoviesRepository(
        moviesRepository: MoviesRepositoryImpl,
    ): MoviesRepository

    companion object {

        @Singleton
        @Provides
        fun provideMovieApi(retrofit: Retrofit): MovieApi = retrofit.create(
            MovieApi::class.java
        )
    }
}