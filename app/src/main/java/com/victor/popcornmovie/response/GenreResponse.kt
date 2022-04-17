package com.victor.popcornmovie.response

import com.google.gson.annotations.SerializedName

data class GenreResponse(
    @SerializedName("genres") var genres: ArrayList<GenreItemResponse>
)

data class GenreItemResponse(
    @SerializedName("id") var id: Int,
    @SerializedName("name") var name: String,

)