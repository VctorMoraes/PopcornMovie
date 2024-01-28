package com.victor.data.genres.di

import com.victor.data.genres.datasource.remote.api.GenreApi
import com.victor.data.genres.repository.GenresRepository
import com.victor.data.genres.repository.GenresRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface GenresDataModule {

    @Binds
    fun bindsGenresRepository(
        moviesRepository: GenresRepositoryImpl,
    ): GenresRepository

    companion object {

        @Singleton
        @Provides
        fun provideGenreApi(retrofit: Retrofit): GenreApi = retrofit.create(
            GenreApi::class.java
        )
    }
}