package com.victor.popcornmovie

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class PopcornMovieApplication: Application() {
    override fun onCreate() {
        super.onCreate()
    }
}