package com.victor.popcornmovie.view.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel: ViewModel() {
    var networkState = MutableLiveData<NetworkState>()
}