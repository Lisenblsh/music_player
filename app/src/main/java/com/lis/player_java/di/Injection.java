package com.lis.player_java.di;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;

import com.lis.player_java.viewModel.PlaybackViewModelFactory;

public class Injection {

    @NonNull
    public static final Injection INSTANCE;

    @NonNull
    public final ViewModelProvider.Factory provideViewModelFactory() {
        return new PlaybackViewModelFactory();
    }

    static {
        INSTANCE = new Injection();
    }
}
