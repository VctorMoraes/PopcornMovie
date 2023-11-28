package com.victor.data.genres.mapper

import com.victor.core.database.entities.GenreEntity
import com.victor.core.model.GenreModel
import com.victor.data.genres.datasource.remote.dto.GenreItemDTO

fun GenreItemDTO.toModel(): GenreModel {
    return GenreModel(id = this.id, name = this.name)
}

fun GenreItemDTO.toEntity(): GenreEntity {
    return GenreEntity(id = this.id, name = this.name)
}

fun GenreEntity.toModel(): GenreModel {
    return GenreModel(id = this.id, name = this.name)
}

fun GenreModel.toDto(): GenreItemDTO {
    return GenreItemDTO(id = this.id, name = this.name)
}

