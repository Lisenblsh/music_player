package com.lis.player_java.di

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.lis.player_java.data.network.retrofit.RetrofitService
import com.lis.player_java.data.repository.MusicRepository
import com.lis.player_java.viewModel.PlaybackViewModelFactory

object Injection {
    private fun provideRepository(userAgent: String, token: String): MusicRepository {
        return MusicRepository(RetrofitService.create(userAgent), token)
    }

    fun provideViewModelFactory(
        userAgent: String,
        token: String,
        context: Context
    ): ViewModelProvider.Factory {
        return PlaybackViewModelFactory(provideRepository(userAgent, token), context)
    }
}