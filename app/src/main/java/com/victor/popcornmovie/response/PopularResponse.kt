package com.victor.popcornmovie.response

import com.google.gson.annotations.SerializedName

data class PopularResponse(
    @SerializedName("page") var page: Int,
    @SerializedName("results") var results: ArrayList<ResultsResponse>,
    @SerializedName("total_results") var totalResults: Int,
    @SerializedName("total_pages") var totalPages: Int
)