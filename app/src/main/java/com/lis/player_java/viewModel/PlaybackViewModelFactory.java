package com.lis.player_java.viewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.AbstractSavedStateViewModelFactory;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

public final class PlaybackViewModelFactory extends AbstractSavedStateViewModelFactory {
    public PlaybackViewModelFactory() {
    }

    @NonNull
    @Override
    protected <T extends ViewModel> T create(@NonNull String key,
                                             @NonNull Class<T> modelClass,
                                             @NonNull SavedStateHandle handle) {
        if (modelClass.isAssignableFrom(PlaybackViewModel.class)) {
            return (T) new PlaybackViewModel();
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
