package com.lis.player_java.viewModel;

import android.app.Application;
import android.media.MediaPlayer;
import android.os.Handler;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.lis.player_java.data.repository.MusicRepository;
import com.lis.player_java.tool.LoopingState;

public class PlaybackViewModel extends ViewModel {
    private final Application application;
    private final MusicRepository repository;
    private MediaPlayer mediaPlayer;
    private MediaPlayer nextPlayer;
    private final Handler myHandler = new Handler();

    private int currentSong = 0;

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

    public LiveData<Boolean> isPlaying() {
        return isPlaying;
    }

    private final MutableLiveData<LoopingState> loopingState = new MutableLiveData<>();

    public LiveData<LoopingState> getLoopingState() {
        return loopingState;
    }

    public void setLoopingState(LoopingState loopingState) {
        this.loopingState.setValue(loopingState);
        mediaPlayer.setLooping(loopingState == LoopingState.SingleLoop);
    }

    PlaybackViewModel(Application application,
                      MusicRepository repository) {
        this.application = application;
        this.repository = repository;
    }

    public void setupMediaPlayer(int song) {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }

        mediaPlayer = MediaPlayer.create(application.getApplicationContext(), song);
        mediaPlayer.setOnCompletionListener(mp -> {
            boolean isLastSong = nextSong();
            if (isLastSong) {
                mp.stop();
            }

        });
        position.setValue((double) mediaPlayer.getCurrentPosition());
        duration.setValue((double) mediaPlayer.getDuration());
        isPlaying.setValue(mediaPlayer.isPlaying());
        loopingState.setValue(LoopingState.NotLoop);
        //mediaPlayer.prepareAsync();
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

    public void seekTo(int progress) {
        mediaPlayer.seekTo(progress);
        position.postValue((double) mediaPlayer.getCurrentPosition());

    }

    public boolean nextSong() {
        if (currentSong != 2) {
            currentSong++;
            int i = repository.getMusicList()[currentSong];
            setupMediaPlayer(i);
            start();
            return true;
        }
        return false;
    }

    public void prevSong() {
        if (mediaPlayer.getCurrentPosition() > 2000) {
            seekTo(0);
        } else {
            if (currentSong != 0) {
                currentSong--;
                int i = repository.getMusicList()[currentSong];
                setupMediaPlayer(i);
                start();
            }
        }
    }

    private final Runnable UpdateSongTime = new Runnable() {
        public void run() {
            position.postValue((double) mediaPlayer.getCurrentPosition());
            myHandler.postDelayed(this, 100);
        }
    };
}