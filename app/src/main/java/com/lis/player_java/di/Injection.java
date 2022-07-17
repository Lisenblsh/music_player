package com.lis.player_java.di;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;

import com.lis.player_java.viewModel.PlaybackViewModelFactory;

public class Injection {

    @NonNull
    public static final Injection INSTANCE = new Injection();

    @NonNull
    public final ViewModelProvider.Factory provideViewModelFactory(Application application) {
        return new PlaybackViewModelFactory(application);
    }
}
