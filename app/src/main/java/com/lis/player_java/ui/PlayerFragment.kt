package com.lis.player_java.ui

import android.content.Context

import com.lis.player_java.viewModel.PlaybackViewModel
import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import android.widget.Toast
import com.lis.player_java.tool.ImageFun
import com.lis.player_java.data.room.MusicDB
import com.lis.player_java.R
import com.google.android.exoplayer2.Player
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.SeekBar
import android.content.SharedPreferences
import android.icu.text.SimpleDateFormat
import android.view.View
import androidx.fragment.app.Fragment
import com.lis.player_java.databinding.FragmentPlayerBinding
import com.lis.player_java.di.Injection
import java.text.MessageFormat
import java.text.ParseException
import java.util.*
import java.util.concurrent.TimeUnit

class PlayerFragment : Fragment() {
    private lateinit var binding: FragmentPlayerBinding
    private lateinit var viewModel: PlaybackViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPlayerBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(
            this, factory
        )[PlaybackViewModel::class.java]
        binding.bindElement()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    private fun FragmentPlayerBinding.bindElement() {
        viewModel.duration.observe(viewLifecycleOwner) { songDuration: Long ->
            Toast.makeText(requireContext(), "" + songDuration, Toast.LENGTH_SHORT).show()
            songProgress.max = songDuration.toInt()
            this.songDuration.text = getStringSongDuration(songDuration)
        }
        val imageFun = ImageFun()
        viewModel.musicInfo.observe(viewLifecycleOwner) { musicInfo: MusicDB? ->
            if (musicInfo != null) {
                val image = musicInfo.photo1200
                if (image.isNotEmpty()) {
                    imageFun.setImage(songImage, image)
                    imageFun.setImageToBackground(backgroundImage, image)
                } else {
                    imageFun.setImage(songImage, R.drawable.song_image)
                    imageFun.setImageToBackground(backgroundImage, R.drawable.song_image)
                }
                songName.text = musicInfo.title
                songName.isSelected = true
                songAuthor.text = musicInfo.artist
            }
        } //заполнение сведений о песне
        viewModel.position.observe(viewLifecycleOwner) { position: Long ->
            songPosition.text = getStringSongDuration(position)
            songProgress.progress = position.toInt()
        } //установка текущей позиции
        viewModel.downloadPosition.observe(viewLifecycleOwner) { downloadPosition: Long ->
            songProgress.secondaryProgress = downloadPosition.toInt()
        }
        viewModel.isPlaying.observe(viewLifecycleOwner) { isPlaying: Boolean ->
            imageFun.setImage(
                buttonPlayPause,
                if (isPlaying) R.drawable.ic_baseline_pause_24 else R.drawable.ic_baseline_play_arrow_24
            )
        } //установка иконки play\pause кнопки

        viewModel.repeatMode.observe(viewLifecycleOwner) { repeatMode: Int ->
            when (repeatMode) {
                Player.REPEAT_MODE_OFF -> imageFun.setImage(
                    buttonLoop, R.drawable.ic_baseline_plus_one_24
                )
                Player.REPEAT_MODE_ONE -> imageFun.setImage(
                    buttonLoop,
                    R.drawable.ic_baseline_loop_24
                )
                Player.REPEAT_MODE_ALL -> imageFun.setImage(
                    buttonLoop,
                    R.drawable.ic_baseline_low_priority_24
                )
            }
        } // установка иконки зацикливания
        viewModel.count.observe(viewLifecycleOwner) { count: Int ->
            Toast.makeText(
                requireContext(),
                "" + count,
                Toast.LENGTH_SHORT
            ).show()
            //TODO("delete this")
        }
        songProgress.setOnSeekBarChangeListener(seekBarSelectProgressListener())
        buttonPlayPause.setOnClickListener { startClickListener() }
        buttonNext.setOnClickListener { nextClickListener() }
        buttonPrevious.setOnClickListener { prevClickListener() }
        buttonLoop.setOnClickListener { loopClickListener() }
    }

    private var repeatMode = Player.REPEAT_MODE_OFF
    private fun loopClickListener() {
        when (repeatMode) {
            Player.REPEAT_MODE_OFF -> repeatMode = Player.REPEAT_MODE_ONE
            Player.REPEAT_MODE_ONE -> repeatMode = Player.REPEAT_MODE_ALL
            Player.REPEAT_MODE_ALL -> repeatMode = Player.REPEAT_MODE_OFF
        }
        viewModel.setRepeatMode(repeatMode)
    }

    private fun startClickListener() {
        var isPlaying = java.lang.Boolean.TRUE == viewModel.isPlaying.value
        if (isPlaying) {
            viewModel.pause()
        } else {
            viewModel.play()
        }
        isPlaying = java.lang.Boolean.TRUE == viewModel.isPlaying.value
    }

    private fun nextClickListener() {
        viewModel.nextSong()
    }

    private fun prevClickListener() {
        viewModel.prevSong()
    }

    private fun seekBarSelectProgressListener(): OnSeekBarChangeListener {
        return object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {}
            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {
                viewModel.seekTo(seekBar.progress.toLong())
            }
        }
    }

    private fun getStringSongDuration(songDuration: Long): String {
        val hours = TimeUnit.MILLISECONDS.toHours(songDuration).toInt()
        val minutes = (TimeUnit.MILLISECONDS.toMinutes(songDuration)
                - TimeUnit.HOURS.toMinutes(hours.toLong())).toInt()
        val seconds = (TimeUnit.MILLISECONDS.toSeconds(songDuration)
                - TimeUnit.MINUTES.toSeconds(minutes.toLong())
                - TimeUnit.HOURS.toSeconds(hours.toLong())).toInt()
        return convertTimeFormat(hours, minutes, seconds)
    }

    private fun convertTimeFormat(hours: Int, minutes: Int, seconds: Int): String {
        var oldFormat = "m:s"
        var newFormat = "mm:ss"
        var stringSongDuration = MessageFormat.format("{0}:{1}", minutes, seconds)
        if (hours > 0) {
            oldFormat = "h:m:s"
            newFormat = "hh:mm:ss"
            stringSongDuration = MessageFormat.format("{0}:{1}:{2}", hours, minutes, seconds)
        }
        try {
            stringSongDuration = SimpleDateFormat(newFormat, Locale.getDefault())
                .format(SimpleDateFormat(oldFormat, Locale.getDefault()).parse(stringSongDuration))
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return stringSongDuration
    }

    private val preference: SharedPreferences
        get() = requireActivity().getSharedPreferences(
            getString(R.string.authorization_info),
            Context.MODE_PRIVATE
        )

    //Добавть проерку userAgent ибо малоли что может произойти перебздеть стоит.
    private val factory: ViewModelProvider.Factory
        get() {
            val userAgent = preference.getString(resources.getString(R.string.user_agent), "")
            val token = preference.getString(resources.getString(R.string.token_key), "")
            //Добавть проерку userAgent ибо малоли что может произойти перебздеть стоит.
            return Injection.provideViewModelFactory(userAgent!!, token!!, requireContext())
        }
}