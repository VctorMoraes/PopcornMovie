package com.victor.popcornmovie.model

open class MovieModel(
    var genreIds: ArrayList<Int>,
    var id: Int,
    var originalLanguage: String,
    var originalTitle: String,
    var overview: String,
    var posterPath: String?,
    var releaseDate: String,
    var title: String,
    var voteCount: Int
)