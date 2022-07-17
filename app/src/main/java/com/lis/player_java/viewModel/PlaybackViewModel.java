package com.lis.player_java.viewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.AbstractSavedStateViewModelFactory;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

public class PlaybackViewModel extends ViewModel {

    private MutableLiveData<Long> position;

    public LiveData<Long> getPosition() {
        return position;
    }

    private MutableLiveData<Long> duration;

    public LiveData<Long> getDuration() {
        return duration;
    }

    private MutableLiveData<Boolean> isPlaying;

    public LiveData<Boolean> getIsPlaying() {
        return isPlaying;
    }

    PlaybackViewModel() {

    }
}

