package com.lis.player_java.viewModel;

import android.app.Application;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.lis.player_java.data.Model.Item;
import com.lis.player_java.data.Model.VkMusic;
import com.lis.player_java.data.repository.MusicRepository;
import com.lis.player_java.tool.LoopingState;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlaybackViewModel extends ViewModel {
    private final Application application;
    private final MusicRepository repository;
    private final MediaPlayer mediaPlayer = new MediaPlayer();
    private final Handler myHandler = new Handler();

    private int currentSong = 0;

    private final MutableLiveData<Double> position = new MutableLiveData<>();

    public LiveData<Double> getPosition() {
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

    private MutableLiveData<Item> musicInfo = new MutableLiveData<>();

    public LiveData<Item> getMusicInfo() {
        return musicInfo;
    }

    private MutableLiveData<VkMusic> musicList = new MutableLiveData<>();

    private Item[] musicList2;

    private void setMusicList(VkMusic value) {
        musicList.setValue(value);
        musicList2 = value.getResponse().getItems();
        musicInfo.setValue(value.getResponse().getItems()[0]);
    }

    PlaybackViewModel(Application application,
                      MusicRepository repository) {
        this.application = application;
        this.repository = repository;
        getMusicList();
    }

    private void setupMediaPlayer(String song) {
        mediaPlayer.reset();
        try {
            mediaPlayer.setDataSource(song);
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(mediaPlayer -> {
                start();
                position.setValue((double) mediaPlayer.getCurrentPosition());
                duration.setValue((double) mediaPlayer.getDuration());
            });
            mediaPlayer.setOnCompletionListener(mediaPlayer-> {
                nextSong();
            });
        } catch (IOException e) {
            e.printStackTrace();

            //Тут надо будет с ошибками разобраться
        }
    }

    private void getMusicList() {
        repository.getMusicList().enqueue(new Callback<VkMusic>() {
            @Override
            public void onResponse(@NonNull Call<VkMusic> call, @NonNull Response<VkMusic> response) {
                if (response.isSuccessful() && response.body().getResponse() != null) {
                    setMusicList(response.body());
                    String song = response.body().getResponse().getItems()[0].getURL();
                    setupMediaPlayer(song);
                }
                //добавить обработчик ошибок
            }

            @Override
            public void onFailure(Call<VkMusic> call, Throwable t) {
                //и сюда
            }
        });
    }

    public void start() {
        Log.e("Stat", "Start");
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
        int playListLength = musicList2.length;
        if (currentSong < playListLength) {
            currentSong++;
            String song = musicList2[currentSong].getURL();
            musicInfo.setValue(musicList2[currentSong]);
            setupMediaPlayer(song);
            return true;
        }
        return false;
    }

    public void prevSong() {
        if (mediaPlayer.getCurrentPosition() > 2000) {
            seekTo(0);
        } else {
            if (currentSong > 0) {
                currentSong--;
                String song = musicList2[currentSong].getURL();
                musicInfo.setValue(musicList2[currentSong]);
                setupMediaPlayer(song);
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