package com.victor.popcornmovie.local.dao

import androidx.room.*
import com.victor.popcornmovie.local.entities.Genre

@Dao
interface GenreDao {
    @Query("SELECT * FROM Genre")
    fun getAll(): List<Genre>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg genres: Genre)

}