package com.lis.player_java.viewModel

import android.media.MediaPlayer
import android.os.Handler
import androidx.lifecycle.*
import com.lis.player_java.data.model.Item
import com.lis.player_java.data.model.VkMusic
import com.lis.player_java.data.repository.MusicRepository
import com.lis.player_java.tool.LoopingState
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
    val successGetMusic = MutableLiveData<Boolean>()
    private val position = MutableLiveData<Double>()
    fun getPosition(): LiveData<Double> {
        return position
    }

    private val duration = MutableLiveData<Double>()

    @JvmField
    var downloadPosition = MutableLiveData<Int>()
    fun getDownloadPosition(): LiveData<Int> {
        return downloadPosition
    }

    fun getDuration(): LiveData<Double> {
        return duration
    }

    private val isPlaying = MutableLiveData<Boolean>()
    fun isPlaying(): LiveData<Boolean> {
        return isPlaying
    }

    private val loopingState = MutableLiveData<LoopingState>()
    fun getLoopingState(): LiveData<LoopingState> {
        return loopingState
    }

    fun setLoopingState(loopingState: LoopingState) {
        this.loopingState.value = loopingState
        mediaPlayer.isLooping = loopingState === LoopingState.SingleLoop
    }

    private val musicInfo = MutableLiveData<Item>()
    fun getMusicInfo(): LiveData<Item> {
        return musicInfo
    }

    private val musicList = MutableLiveData<VkMusic?>()
    private fun getMusicList(): List<Item> {
        return musicList.value!!.response.items
    }

    private fun setMusicList(value: VkMusic?) {
        musicList.value = value
        musicInfo.value = value!!.response.items[0]
    }

    private fun setupMediaPlayer(currentSong: Int) {
        val song: String = getMusicList()[currentSong].url
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

    private val musicListFromRepo: Unit
        private get() {
            repository.getMusicList(10, 0).enqueue(object : Callback<VkMusic?> {
                override fun onResponse(call: Call<VkMusic?>, response: Response<VkMusic?>) {
                    if (response.isSuccessful
                        && response.body() != null && response.body()!!.response != null
                    ) {
                        setMusicList(response.body())
                        setupMediaPlayer(0)
                        successGetMusic.setValue(true)
                    } else {
                        successGetMusic.setValue(false)
                    }
                }

                override fun onFailure(call: Call<VkMusic?>, t: Throwable) {
                    successGetMusic.setValue(false)
                }
            })
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

    fun nextSong() {
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

    fun prevSong() {
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