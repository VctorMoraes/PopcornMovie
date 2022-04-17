package com.victor.popcornmovie.api

import android.content.Context
import androidx.room.Room
import com.victor.popcornmovie.local.PopcornMovieDatabase
import com.victor.popcornmovie.local.dao.GenreDao
import com.victor.popcornmovie.repository.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {

    @Singleton
    @Provides
    fun providesHttpLoggingInterceptor() = HttpLoggingInterceptor()
        .apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Singleton
    @Provides
    fun providesOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient
            .Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()


    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Singleton
    @Provides
    fun providesRepository(apiService: ApiService) = MovieRepository(apiService)

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext app: Context): PopcornMovieDatabase {
        return Room.databaseBuilder(
            app.applicationContext,
            PopcornMovieDatabase::class.java,
            PopcornMovieDatabase.DATABASE_NAME
        ).allowMainThreadQueries().build()
    }

    @Singleton
    @Provides
    fun provideGenreDao(database: PopcornMovieDatabase): GenreDao {
        return database.genreDao()
    }

    companion object {
        private const val BASE_URL = "https://api.themoviedb.org/3/"
    }
}