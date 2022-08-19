package com.lis.player_java.di

import androidx.lifecycle.ViewModelProvider
import com.lis.player_java.data.network.retrofit.RetrofitService
import com.lis.player_java.data.repository.MusicRepository
import com.lis.player_java.viewModel.PlaybackViewModelFactory
import com.lis.player_java.di.Injection

object Injection {
    private fun provideRepository(userAgent: String, token: String): MusicRepository {
        return MusicRepository(RetrofitService.create(userAgent), token)
    }

    fun provideViewModelFactory(userAgent: String, token: String): ViewModelProvider.Factory {
        return PlaybackViewModelFactory(provideRepository(userAgent, token))
    }
}