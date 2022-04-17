package com.victor.popcornmovie.view.base

import android.content.Context
import android.content.Intent
import com.victor.popcornmovie.view.main.MainActivity

object NavigationService {

    const val EXTRA_HAD_LOADED_GENRE_LIST = "EXTRA_HAD_LOADED_GENRE_LIST"

    fun launchMainActivity(context: Context, hasLoadedGenres: Boolean = false) {
        val intent = Intent(context, MainActivity::class.java)
        intent.putExtra(EXTRA_HAD_LOADED_GENRE_LIST, hasLoadedGenres)

        context.startActivity(intent)
    }
}