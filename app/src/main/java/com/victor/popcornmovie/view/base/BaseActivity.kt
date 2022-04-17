package com.victor.popcornmovie.view.base

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<T : ViewBinding> : AppCompatActivity() {

    lateinit var viewBinding: T

    fun <T> observe(liveData: MutableLiveData<T>, observer: Observer<in T>) {
        liveData.observe(this, observer)
    }
}