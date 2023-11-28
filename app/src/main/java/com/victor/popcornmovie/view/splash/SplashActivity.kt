package com.victor.popcornmovie.view.splash

import android.os.Bundle
import androidx.activity.viewModels
import com.victor.popcornmovie.databinding.ActivitySplashBinding
import com.victor.popcornmovie.view.base.BaseActivity
import com.victor.popcornmovie.view.base.NavigationService
import com.victor.popcornmovie.view.base.NetworkState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding>() {
    private val viewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        NavigationService.launchMainActivity(this)
        finish()
//        initiateObservers()
//        viewModel.loadGenres()
    }

    private fun initiateObservers() {
        observe(viewModel.networkState) {
            when (it.status) {
                NetworkState.NetworkStateStatus.RUNNING -> {

                }
                NetworkState.NetworkStateStatus.SUCCESS,
                NetworkState.NetworkStateStatus.ERROR -> {
                    NavigationService.launchMainActivity(this)
                    finish()
                }
            }
        }
    }
}