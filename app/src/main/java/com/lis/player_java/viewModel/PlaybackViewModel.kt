package com.lis.player_java.viewModel

import android.content.Context
import android.media.session.PlaybackState
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.*
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.lis.player_java.data.model.Item
import com.lis.player_java.data.repository.MusicRepository
import com.lis.player_java.data.room.AlbumForMusic
import com.lis.player_java.data.room.GenreType
import com.lis.player_java.data.room.MusicDB
import com.lis.player_java.data.room.MusicDatabase
import com.lis.player_java.tool.DiffAudioData
import com.lis.player_java.tool.convertToMusicDB
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.coroutineContext

class PlaybackViewModel(
    private val repository: MusicRepository,
    private val context: Context
) : ViewModel() {
    private val exoPlayer: ExoPlayer = ExoPlayer.Builder(context).build()
    private val myHandler = Handler(Looper.getMainLooper())
    private val differ = DiffAudioData(viewModelScope.coroutineContext, exoPlayer)
    val currentSong: MutableLiveData<Long> = MutableLiveData(0)
    val position = MutableLiveData<Long>()

    val duration = MutableLiveData<Long>()

    val downloadPosition = MutableLiveData<Long>()

    val isPlaying = MutableLiveData<Boolean>()

    val repeatMode = MutableLiveData<Int>()

    val count = MutableLiveData<Int>()

    fun setRepeatMode(repeatMode: Int) {
        exoPlayer.repeatMode = repeatMode
        this.repeatMode.value = repeatMode
    }

    private lateinit var audiosList: ArrayList<MusicDB>

    val musicInfo = MutableLiveData<MusicDB>()

    private fun setupMediaPlayer(currentSong: Int) {
        position.value = exoPlayer.currentPosition
        duration.value = exoPlayer.duration
    }

    fun play() {
        exoPlayer.play()
        isPlaying.value = exoPlayer.isPlaying

        myHandler.postDelayed(updateSongTime, 100)
    }

    fun pause() {
        exoPlayer.pause()
        isPlaying.value = exoPlayer.isPlaying
        myHandler.removeCallbacks(updateSongTime)
    }

    fun seekTo(progress: Long) {
        exoPlayer.seekTo(progress)
        position.postValue(exoPlayer.currentPosition)
    }

    fun nextSong() {
        exoPlayer.seekToNextMediaItem()

    }

    fun prevSong() {
        exoPlayer.seekToPrevious()
    }

    private val updateSongTime: Runnable = object : Runnable {
        override fun run() {
            position.value = exoPlayer.currentPosition
            downloadPosition.value = exoPlayer.bufferedPosition
            myHandler.postDelayed(this, 100)
        }
    }

    init {
        downloadPosition.value = 0
        duration.value = 0
        position.value = 0
        isPlaying.value = false
        repeatMode.value = exoPlayer.repeatMode
        exoPlayer.addListener(object : Player.Listener {
            override fun onPlaybackStateChanged(playbackState: Int) {
                if (playbackState == ExoPlayer.STATE_READY || playbackState == PlaybackState.STATE_FAST_FORWARDING) {
                    duration.value = exoPlayer.duration
                    musicInfo.value =
                        audiosList.find { it.id.toString() == exoPlayer.currentMediaItem?.mediaId }
                }
            }
        })
        viewModelScope.launch {
            audiosList = (getAudios(4, 0) ?: emptyList()) as ArrayList<MusicDB>
            differ.add(audiosList)
            exoPlayer.seekTo(currentSong.value?.toInt()?:0, 0)
            exoPlayer.prepare()
            count.value = exoPlayer.mediaItemCount
        }
    }

    private suspend fun getAudios(count: Int, offset: Int): List<MusicDB>? {
        return repository.getMusicList(count, offset).body()?.response?.items?.convertToMusicDB()
    }


}

class ViewModelFactory(
    private val musicRepository: MusicRepository,
    private val context: Context,
    private val database: MusicDatabase
) :
    AbstractSavedStateViewModelFactory() {
    override fun <T : ViewModel?> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): T {
        if (modelClass.isAssignableFrom(PlaybackViewModel::class.java)) {
            return PlaybackViewModel(musicRepository, context) as T
        } else if (modelClass.isAssignableFrom(MusicListViewModel::class.java)) {
            return MusicListViewModel(musicRepository, database) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}