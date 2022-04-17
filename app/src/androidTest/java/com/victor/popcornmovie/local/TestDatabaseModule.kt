package com.victor.popcornmovie.local

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
class TestDatabaseModule {

    @Provides
    @Named("test_database")
    fun provideDatabase(@ApplicationContext context: Context) =
        Room.inMemoryDatabaseBuilder(
            context, PopcornMovieDatabase::class.java
        ).allowMainThreadQueries()
            .build()
}