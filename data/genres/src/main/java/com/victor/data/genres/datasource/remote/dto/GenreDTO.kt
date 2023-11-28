package com.victor.data.genres.datasource.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class GenreDTO(
    val genres: ArrayList<GenreItemDTO>
)

@Serializable
data class GenreItemDTO(
    val id: Int,
    val name: String,
)