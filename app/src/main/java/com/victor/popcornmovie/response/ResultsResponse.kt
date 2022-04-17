package com.victor.popcornmovie.response

import com.google.gson.annotations.SerializedName

data class ResultsResponse(
    @SerializedName("genre_ids") var genreIds: ArrayList<Int>,
    @SerializedName("id") var id: Int,
    @SerializedName("original_language") var originalLanguage: String,
    @SerializedName("original_title") var originalTitle: String,
    @SerializedName("overview") var overview: String,
    @SerializedName("poster_path") var posterPath: String?,
    @SerializedName("release_date") var releaseDate: String,
    @SerializedName("title") var title: String,
    @SerializedName("vote_count") var voteCount: Int
)