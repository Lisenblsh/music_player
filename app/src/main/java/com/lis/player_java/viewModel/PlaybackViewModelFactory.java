package com.lis.player_java.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AbstractSavedStateViewModelFactory;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import com.lis.player_java.repository.MusicRepository;

public final class PlaybackViewModelFactory extends AbstractSavedStateViewModelFactory {
    Application application;
    public PlaybackViewModelFactory(Application application) {
        this.application = application;
    }

    @NonNull
    @Override
    protected <T extends ViewModel> T create(@NonNull String key,
                                             @NonNull Class<T> modelClass,
                                             @NonNull SavedStateHandle handle) {
        if (modelClass.isAssignableFrom(PlaybackViewModel.class)) {
            return (T) new PlaybackViewModel(application, new MusicRepository());
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
