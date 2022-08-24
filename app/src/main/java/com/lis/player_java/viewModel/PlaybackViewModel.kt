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
import com.lis.player_java.tool.DiffAudioData
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PlaybackViewModel(
    private val repository: MusicRepository,
    private val context: Context
) : ViewModel() {
    private val exoPlayer: ExoPlayer = ExoPlayer.Builder(context)
        .build()
    private val myHandler = Handler(Looper.getMainLooper())
    private var currentSong = 0
    val position = MutableLiveData<Long>()

    val duration = MutableLiveData<Long>()

    val downloadPosition = MutableLiveData<Long>()

    val isPlaying = MutableLiveData<Boolean>()

    val repeatMode = MutableLiveData<Int>()
    val count = MutableLiveData<Int>()

    fun setLoopingState(repeatMode: Int) {
        exoPlayer.repeatMode = when (repeatMode) {
            Player.REPEAT_MODE_OFF -> Player.REPEAT_MODE_OFF
            Player.REPEAT_MODE_ONE -> Player.REPEAT_MODE_ONE
            Player.REPEAT_MODE_ALL -> Player.REPEAT_MODE_ALL
            else -> return
        }
    }

    private lateinit var audiosList: List<MusicDB>

    val musicInfo = MutableLiveData<Item>()

    private fun setupMediaPlayer(currentSong: Int) {
        position.value = exoPlayer.currentPosition
        duration.value = exoPlayer.duration
    }

    fun play() {
        exoPlayer.play()
        isPlaying.value = exoPlayer.isPlaying
        Log.e("visPlaying", isPlaying.value.toString())
        Log.e("visPlaying1", exoPlayer.isPlaying.toString())

        myHandler.postDelayed(updateSongTime, 100)
    }

    fun pause() {
        exoPlayer.pause()
        isPlaying.value = exoPlayer.isPlaying
        myHandler.removeCallbacks(updateSongTime)
        Log.e("visPlaying", isPlaying.value.toString())
        Log.e("visPlaying1", exoPlayer.isPlaying.toString())
    }

    fun seekTo(progress: Long) {
        exoPlayer.seekTo(progress)
        position.postValue(exoPlayer.currentPosition)
    }

    fun nextSong() {
        exoPlayer.seekToNextMediaItem()
        Handler().postDelayed({
            viewModelScope.launch {
                audiosList = getAudios(4) ?: emptyList()
                DiffAudioData(coroutineContext).update(exoPlayer,audiosList)
                count.value = exoPlayer.mediaItemCount
            }
        },1000)

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
        exoPlayer.addListener(object : Player.Listener {
            override fun onPlaybackStateChanged(playbackState: Int) {
                if (playbackState == ExoPlayer.STATE_READY) {
                    duration.value = exoPlayer.duration
                }
                if (playbackState == PlaybackState.STATE_FAST_FORWARDING) {
                    duration.value = exoPlayer.duration
                }
            }

        })
        viewModelScope.launch {
            audiosList = getAudios(2) ?: emptyList()
            val playList = audiosList.map {
                MediaItem.fromUri(it.url)
            }
            exoPlayer.setMediaItems(playList)

            exoPlayer.prepare()
            count.value = exoPlayer.mediaItemCount
        }
    }

    private suspend fun getAudios(count: Int): List<MusicDB>? {
        return repository.getMusicList(count, 0).body()?.response?.items?.map {
            MusicDB(
                it.id,
                it.ownerId,
                "${it.id}_${it.ownerId}",
                it.artist,
                it.title,
                it.url,
                it.album?.thumb?.photo600 ?: "",
                it.album?.thumb?.photo1200 ?: "",
                it.duration,
                it.isExplicit,
                AlbumForMusic(it.id, it.ownerId, it.accessKey),
                it.lyricsId ?: 0,
                GenreType.getGenreById(it.genreId?.toInt() ?: 0)
            )
        }
    }


}

class PlaybackViewModelFactory(
    private val musicRepository: MusicRepository,
    private val context: Context
) :
    AbstractSavedStateViewModelFactory() {
    override fun <T : ViewModel?> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): T {
        if (modelClass.isAssignableFrom(PlaybackViewModel::class.java)) {
            return PlaybackViewModel(musicRepository, context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}