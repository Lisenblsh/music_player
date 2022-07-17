package com.lis.player_java.viewModel;

import android.app.Application;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Handler;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PlaybackViewModel extends ViewModel {
    private final Application application;
    private MediaPlayer mediaPlayer;
    private final Handler myHandler = new Handler();

    private final MutableLiveData<Double> position = new MutableLiveData<>();

    public LiveData<Double> getPosition() {
        position.postValue((double) mediaPlayer.getCurrentPosition());
        return position;
    }

    private final MutableLiveData<Double> duration = new MutableLiveData<>();

    public LiveData<Double> getDuration() {
        return duration;
    }

    private final MutableLiveData<Boolean> isPlaying = new MutableLiveData<>();

    public LiveData<Boolean> getIsPlaying() {
        return isPlaying;
    }

    PlaybackViewModel(Application application) {
        this.application = application;
    }

    public void setupMediaPlayer(int song) {
        mediaPlayer = MediaPlayer.create(application.getApplicationContext(), song);
        mediaPlayer.setOnCompletionListener(MediaPlayer::stop);
        position.postValue((double) mediaPlayer.getCurrentPosition());
        duration.postValue((double) mediaPlayer.getDuration());
        isPlaying.postValue(mediaPlayer.isPlaying());
    }

    public void start() {
        mediaPlayer.start();
        isPlaying.postValue(mediaPlayer.isPlaying());
        myHandler.postDelayed(UpdateSongTime, 100);
    }

    public void pause() {
        mediaPlayer.pause();
        isPlaying.postValue(mediaPlayer.isPlaying());
        myHandler.removeCallbacks(UpdateSongTime);

    }

    public void seekTo(int progress){
        mediaPlayer.seekTo(progress);
        position.postValue((double) mediaPlayer.getCurrentPosition());

    }

    private final Runnable UpdateSongTime = new Runnable() {
        public void run() {
            position.postValue((double) mediaPlayer.getCurrentPosition());
            myHandler.postDelayed(this, 100);
        }
    };
}

