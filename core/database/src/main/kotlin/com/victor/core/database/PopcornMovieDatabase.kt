package com.victor.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.victor.core.database.dao.GenreDao
import com.victor.core.database.entities.GenreEntity

@Database(version = 1, entities = [GenreEntity::class], exportSchema = false)
abstract class PopcornMovieDatabase : RoomDatabase() {
    abstract fun genreDao(): GenreDao

    companion object {
        const val DATABASE_NAME = "PopcornMovie"
    }
}