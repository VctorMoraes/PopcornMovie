package com.victor.popcornmovie.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Genre(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id") var id: Int,
    @ColumnInfo(name = "name") var name: String
)