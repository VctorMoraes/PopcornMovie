package com.victor.popcornmovie.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.victor.popcornmovie.local.dao.GenreDao
import com.victor.popcornmovie.local.entities.Genre

@Database(version = 1, entities = [Genre::class], exportSchema = false)
abstract class PopcornMovieDatabase: RoomDatabase() {
    abstract fun genreDao() : GenreDao

    companion object {
        const val DATABASE_NAME = "PopcornMovie"
    }
}