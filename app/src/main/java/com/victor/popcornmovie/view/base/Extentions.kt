package com.victor.popcornmovie.view.base

import android.view.View
import androidx.lifecycle.MutableLiveData

fun MutableLiveData<NetworkState>.running(detail: String? = null) {
    this.postValue(NetworkState(NetworkState.NetworkStateStatus.RUNNING, detail))
}

fun MutableLiveData<NetworkState>.success(detail: String? = null) {
    this.postValue(NetworkState(NetworkState.NetworkStateStatus.SUCCESS, detail))
}

fun MutableLiveData<NetworkState>.error(detail: String? = null) {
    this.postValue(NetworkState(NetworkState.NetworkStateStatus.ERROR, detail))
}

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}
fun View.invisible() {
    this.visibility = View.INVISIBLE
}
