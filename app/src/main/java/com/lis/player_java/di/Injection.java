package com.lis.player_java.di;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;

import com.lis.player_java.data.network.retrofit.RetrofitClient;
import com.lis.player_java.data.repository.MusicRepository;
import com.lis.player_java.viewModel.PlaybackViewModelFactory;

public class Injection {

    @NonNull
    public static final Injection INSTANCE = new Injection();

    private MusicRepository provideRepository(String userAgent) {
        return new MusicRepository(RetrofitClient.create(userAgent));
    }

    @NonNull
    public final ViewModelProvider.Factory provideViewModelFactory(Application application, String userAgent) {
        return new PlaybackViewModelFactory(application, provideRepository(userAgent));
    }
}
