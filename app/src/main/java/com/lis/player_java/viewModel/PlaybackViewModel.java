package com.lis.player_java.viewModel;

import android.media.MediaPlayer;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.lis.player_java.data.model.Item;
import com.lis.player_java.data.model.VkMusic;
import com.lis.player_java.data.repository.MusicRepository;
import com.lis.player_java.tool.LoopingState;

import java.io.IOException;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlaybackViewModel extends ViewModel {
    private final MusicRepository repository;
    private final MediaPlayer mediaPlayer = new MediaPlayer();
    private final Handler myHandler = new Handler();

    private int currentSong = 0;

    private MutableLiveData<Boolean> isSuccessGetMusic = new MutableLiveData<>();

    public MutableLiveData<Boolean> getSuccessGetMusic() {
        return isSuccessGetMusic;
    }

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

    private final MutableLiveData<Item> musicInfo = new MutableLiveData<>();

    public LiveData<Item> getMusicInfo() {
        return musicInfo;
    }

    private final MutableLiveData<VkMusic> musicList = new MutableLiveData<>();

    @NonNull
    private Item[] getMusicList() {
        return musicList.getValue().getResponse().getItems();
    }

    private void setMusicList(VkMusic value) {
        musicList.setValue(value);
        musicInfo.setValue(value.getResponse().getItems()[0]);
    }

    PlaybackViewModel(MusicRepository repository) {
        this.repository = repository;
        duration.setValue(0d);
        isSuccessGetMusic.setValue(true);
        mediaPlayer.setOnPreparedListener(mediaPlayer -> {
            start();
            if (Objects.requireNonNull(getDuration().getValue()).intValue() == 0) {
                duration.setValue((double) mediaPlayer.getDuration());
            }

        });
        mediaPlayer.setOnCompletionListener(mediaPlayer -> {
            if (getLoopingState().getValue() != LoopingState.SingleLoop) {
                nextSong();
            } else {
                seekTo(0);
            }
        });
        getMusicListFromRepo();
    }

    private void setupMediaPlayer(int currentSong) {
        String song = Objects.requireNonNull(getMusicList())[currentSong].getURL();
        musicInfo.setValue(getMusicList()[currentSong]);
        mediaPlayer.reset();
        try {
            mediaPlayer.setDataSource(song);
            position.setValue((double) mediaPlayer.getCurrentPosition());
            Item asd;
            long _duration = ((asd = musicInfo.getValue()) != null)
                    ? asd.getDuration() * 1000 : 0;
            duration.setValue((double) _duration);
            mediaPlayer.prepareAsync();

        } catch (IOException e) {
            e.printStackTrace();
            isSuccessGetMusic.setValue(false);

        } catch (IllegalStateException e) {
            isSuccessGetMusic.setValue(false);
        }
    }

    private void getMusicListFromRepo() {
        repository.getMusicList().enqueue(new Callback<VkMusic>() {
            @Override
            public void onResponse(@NonNull Call<VkMusic> call, @NonNull Response<VkMusic> response) {
                if (response.isSuccessful()
                        && response.body() != null
                        && response.body().getResponse() != null) {
                    setMusicList(response.body());
                    setupMediaPlayer(0);
                    isSuccessGetMusic.setValue(true);
                } else {
                    isSuccessGetMusic.setValue(false);
                }
            }

            @Override
            public void onFailure(@NonNull Call<VkMusic> call, @NonNull Throwable t) {
                isSuccessGetMusic.setValue(false);
            }
        });
    }

    private boolean needToPlay = true;

    public void start() {
        if (!needToPlay) {
            mediaPlayer.start();
        } else {
            needToPlay = false;
        }
        isPlaying.postValue(mediaPlayer.isPlaying());
        myHandler.postDelayed(UpdateSongTime, 100);
    }

    public void pause() {
        needToPlay = true;
        mediaPlayer.pause();
        isPlaying.postValue(mediaPlayer.isPlaying());
        myHandler.removeCallbacks(UpdateSongTime);

    }

    public void seekTo(int progress) {
        mediaPlayer.seekTo(progress);
        position.postValue((double) mediaPlayer.getCurrentPosition());

    }

    public void nextSong() {
        int playListLength = getMusicList().length;
        if (currentSong < playListLength - 1) {
            currentSong++;
            setupMediaPlayer(currentSong);
        } else if (currentSong == playListLength - 1 &&
                getLoopingState().getValue() == LoopingState.PlaylistLoop) {
            currentSong = 0;
            setupMediaPlayer(currentSong);
        }
    }

    public void prevSong() {
        if (mediaPlayer.getCurrentPosition() > 2000) {
            seekTo(0);
        } else {
            if (currentSong > 0) {
                currentSong--;
                setupMediaPlayer(currentSong);
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