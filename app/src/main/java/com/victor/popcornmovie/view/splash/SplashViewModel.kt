package com.victor.popcornmovie.view.splash

import com.victor.popcornmovie.view.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor() :
    BaseViewModel() {

//    fun loadGenres() {
//        networkState.running()
//        viewModelScope.launch {
//            val genreResponse = genreRepository.getGenres()
//            when {
//                genreResponse.isSuccess -> networkState.success()
//                else -> networkState.error()
//            }
//        }
//    }
}