package com.victor.core.database.dao


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.victor.core.database.entities.GenreEntity

@Dao
interface GenreDao {
    @Query("SELECT * FROM GenreEntity")
    fun getAll(): List<GenreEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg genres: GenreEntity)

}