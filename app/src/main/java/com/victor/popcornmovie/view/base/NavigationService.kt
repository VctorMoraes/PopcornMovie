package com.victor.popcornmovie.view.base

import android.content.Context
import android.content.Intent
import com.victor.popcornmovie.view.main.MainActivity

object NavigationService {

    fun launchMainActivity(context: Context) {
        val intent = Intent(context, MainActivity::class.java)
        context.startActivity(intent)
    }
}