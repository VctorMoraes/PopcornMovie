package com.victor.popcornmovie.view.splash

import androidx.lifecycle.viewModelScope
import com.victor.popcornmovie.repository.GenreRepository
import com.victor.popcornmovie.view.base.BaseViewModel
import com.victor.popcornmovie.view.base.error
import com.victor.popcornmovie.view.base.running
import com.victor.popcornmovie.view.base.success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(private val genreRepository: GenreRepository) :
    BaseViewModel() {

    fun loadGenres() {
        networkState.running()
        viewModelScope.launch {
            val genreResponse = genreRepository.getGenres()
            when {
                genreResponse.isSuccess -> networkState.success()
                else -> networkState.error()
            }
        }
    }
}