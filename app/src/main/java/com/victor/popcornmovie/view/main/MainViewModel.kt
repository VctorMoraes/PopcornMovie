package com.victor.popcornmovie.view.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.victor.popcornmovie.model.GenreModel
import com.victor.popcornmovie.model.MovieModel
import com.victor.popcornmovie.repository.GenreRepository
import com.victor.popcornmovie.repository.MovieRepository
import com.victor.popcornmovie.response.ResultsResponse
import com.victor.popcornmovie.view.base.BaseViewModel
import com.victor.popcornmovie.view.base.error
import com.victor.popcornmovie.view.base.running
import com.victor.popcornmovie.view.base.success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
    private val genreRepository: GenreRepository
) : BaseViewModel() {
    val genreList = MutableLiveData<ArrayList<GenreModel>>()
    val movieList = MutableLiveData<ArrayList<MovieModel>>()
    private var currentPage = 1

    fun loadGenres() {
        viewModelScope.launch {
            val genres = genreRepository.getLocalGenres()
            val list: ArrayList<GenreModel> = genres.map {
                GenreModel(it.id, it.name)
            } as ArrayList<GenreModel>
            genreList.postValue(list)
        }
    }

    fun loadMovies(genreId: Int = 0, loadMore: Boolean = false) {
        viewModelScope.launch {
            networkState.running()
            when {
                loadMore -> currentPage++
                else -> currentPage = 1
            }

            val moviesResponse = when (genreId) {
                0 -> movieRepository.getPopularMovies(currentPage)
                else -> movieRepository.getMoviesByGenre(genreId, currentPage)
            }

            when {
                moviesResponse.isSuccessful -> {
                    moviesResponse.body()?.let { response ->
                        movieList.postValue(mapMovieListFromResponse(response.results))
                        networkState.success()
                    } ?: networkState.error()
                }
                else -> networkState.error()
            }
        }
    }

    private fun mapMovieListFromResponse(results: ArrayList<ResultsResponse>): ArrayList<MovieModel> {
        return results.map {
            MovieModel(
                it.genreIds,
                it.id,
                it.originalLanguage,
                it.originalTitle,
                it.overview,
                it.posterPath,
                it.releaseDate,
                it.title,
                it.voteCount
            )
        } as ArrayList<MovieModel>
    }
}