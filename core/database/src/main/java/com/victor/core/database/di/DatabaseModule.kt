package com.victor.core.database.di

import android.content.Context
import androidx.room.Room
import com.victor.core.database.PopcornMovieDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext app: Context): PopcornMovieDatabase {
        return Room.databaseBuilder(
            app.applicationContext,
            PopcornMovieDatabase::class.java,
            PopcornMovieDatabase.DATABASE_NAME,
        ).allowMainThreadQueries().build()
    }
}