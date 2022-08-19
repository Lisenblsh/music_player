package com.lis.player_java.viewModel

import android.media.MediaPlayer
import android.os.Handler
import androidx.lifecycle.*
import com.lis.player_java.data.model.Item
import com.lis.player_java.data.model.VkMusic
import com.lis.player_java.data.repository.MusicRepository
import com.lis.player_java.data.room.model.MusicDB
import com.lis.player_java.tool.LoopingState
import kotlinx.coroutines.android.HandlerDispatcher
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.take
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.util.*

class PlaybackViewModel(
    private val repository: MusicRepository,
    private val listMusicListViewModel: MusicListViewModel
) : ViewModel() {
    private val mediaPlayer = MediaPlayer()
    private val myHandler = Handler()
    private var currentSong = 0
    val position = MutableLiveData<Double>()

    val duration = MutableLiveData<Double>()

    val downloadPosition = MutableLiveData<Int>()

    val isPlaying = MutableLiveData<Boolean>()

    val loopingState = MutableLiveData<LoopingState>()

    fun setLoopingState(loopingState: LoopingState) {
        this.loopingState.value = loopingState
        mediaPlayer.isLooping = loopingState === LoopingState.SingleLoop
    }

    val musicInfo = MutableLiveData<Item>()

    private suspend fun setupMediaPlayer(currentSong: Int) {
        val song = listMusicListViewModel.pagingMusicList.first()
        musicInfo.value = getMusicList()[currentSong]
        mediaPlayer.reset()
        try {
            mediaPlayer.setDataSource(song)
            position.value = mediaPlayer.currentPosition.toDouble()
            downloadPosition.value = 0
            var temp: Item
            val _duration =
                if (musicInfo.value.also { temp = it!! } != null) temp.duration * 1000 else 0
            duration.value = _duration.toDouble()
            mediaPlayer.prepareAsync()
        } catch (e: IOException) {
            e.printStackTrace()
            successGetMusic.setValue(false)
        } catch (e: IllegalStateException) {
            successGetMusic.setValue(false)
        }
    }

    private var needToPlay = true
    fun start() {
        if (!needToPlay) {
            mediaPlayer.start()
        } else {
            needToPlay = false
        }
        isPlaying.postValue(mediaPlayer.isPlaying)
        myHandler.postDelayed(updateSongTime, 100)
    }

    fun pause() {
        needToPlay = true
        mediaPlayer.pause()
        isPlaying.postValue(mediaPlayer.isPlaying)
        myHandler.removeCallbacks(updateSongTime)
    }

    fun seekTo(progress: Int) {
        mediaPlayer.seekTo(progress)
        position.postValue(mediaPlayer.currentPosition.toDouble())
    }

    suspend fun nextSong() {
        val playListLength = getMusicList().size
        if (currentSong < playListLength - 1) {
            currentSong++
            setupMediaPlayer(currentSong)
        } else if (currentSong == playListLength - 1 &&
            getLoopingState().value === LoopingState.PlaylistLoop
        ) {
            currentSong = 0
            setupMediaPlayer(currentSong)
        }
    }

    suspend fun prevSong() {
        if (mediaPlayer.currentPosition > 2000) {
            seekTo(0)
        } else {
            if (currentSong > 0) {
                currentSong--
                setupMediaPlayer(currentSong)
            }
        }
    }

    private val updateSongTime: Runnable = object : Runnable {
        override fun run() {
            position.postValue(mediaPlayer.currentPosition.toDouble())
            myHandler.postDelayed(this, 100)
        }
    }

    init {
        duration.value = 0.0
        successGetMusic.value = true
        mediaPlayer.setOnPreparedListener { mediaPlayer: MediaPlayer ->
            start()
            if (getDuration().value?.toInt() == 0) {
                duration.value = mediaPlayer.duration.toDouble()
            }
        }
        mediaPlayer.setOnCompletionListener { mediaPlayer: MediaPlayer? ->
            if (getLoopingState().value !== LoopingState.SingleLoop) {
                nextSong()
            } else {
                seekTo(0)
            }
        }
        mediaPlayer.setOnBufferingUpdateListener { mediaPlayer: MediaPlayer?, percent: Int ->
            downloadPosition.setValue(
                percent
            )
        }
        musicListFromRepo
    }
}

class PlaybackViewModelFactory(var musicRepository: MusicRepository) :
    AbstractSavedStateViewModelFactory() {
    override fun <T : ViewModel?> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): T {
        if (modelClass.isAssignableFrom(PlaybackViewModel::class.java)) {
            return PlaybackViewModel(musicRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}