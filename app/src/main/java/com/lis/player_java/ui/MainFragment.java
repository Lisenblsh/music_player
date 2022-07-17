package com.lis.player_java.ui;

import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.lis.player_java.ImageFun;
import com.lis.player_java.R;
import com.lis.player_java.databinding.FragmentMainBinding;
import com.lis.player_java.di.Injection;
import com.lis.player_java.player.Music;
import com.lis.player_java.viewModel.PlaybackViewModel;

import java.text.MessageFormat;
import java.text.ParseException;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class MainFragment extends Fragment {

    private FragmentMainBinding binding;
    private final Handler myHandler = new Handler();
    private Music mPlayer;

    private PlaybackViewModel viewModel;

    private double startTime = 0;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mPlayer = new Music(requireContext());
        if(binding == null){
            binding = FragmentMainBinding.inflate(inflater, container, false);
            viewModel = new ViewModelProvider(
                    this, Injection.INSTANCE.provideViewModelFactory()
            ).get(PlaybackViewModel.class);
        }
        bindElement();
        return binding.getRoot();
    }

    private void bindElement() {
        long songDuration = (long) mPlayer.getDuration();
        binding.songProgress.setMax((int) songDuration);


        Log.e("instance", MessageFormat.format("{0}", viewModel.hashCode()));


        binding.songDuration.setText(getStringSongDuration(songDuration));
        binding.songPosition.setText(getStringSongDuration((long) startTime));
        binding.songProgress.setOnSeekBarChangeListener(seekBarSelectProgressListener());

        binding.buttonPlayPause.setOnClickListener(this::startClickListener);

        binding.buttonNext.setOnClickListener(v -> {
        });
        binding.buttonPrevious.setOnClickListener(v -> {
        });
    }

    private final Runnable UpdateSongTime = new Runnable() {
        public void run() {
            startTime = mPlayer.getPosition();
            binding.songPosition.setText(getStringSongDuration((long) startTime));
            binding.songProgress.setProgress((int) startTime);
            myHandler.postDelayed(this, 100);
        }
    };

    private void startClickListener(View v) {
        if (mPlayer.isPlaying()) {
            mPlayer.pause();
            myHandler.removeCallbacks(UpdateSongTime);
            new ImageFun().setImage((ImageView) v, R.drawable.ic_baseline_play_arrow_24);
        } else {
            startTime = mPlayer.getPosition();
            mPlayer.start();
            binding.songProgress.setProgress((int) startTime);
            new ImageFun().setImage((ImageView) v, R.drawable.ic_baseline_pause_24);

            myHandler.postDelayed(UpdateSongTime, 100);
        }
    }

    private SeekBar.OnSeekBarChangeListener seekBarSelectProgressListener() {
        return new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mPlayer.seekTo(seekBar.getProgress());
            }
        };
    }

    private String getStringSongDuration(long songDuration) {
        int hours = (int) TimeUnit.MILLISECONDS.toHours(songDuration);
        int minutes = (int) (TimeUnit.MILLISECONDS.toMinutes(songDuration)
                - TimeUnit.HOURS.toMinutes(hours));
        int seconds = (int) (TimeUnit.MILLISECONDS.toSeconds(songDuration)
                - TimeUnit.MINUTES.toSeconds(minutes)
                - TimeUnit.HOURS.toSeconds(hours));

        return convertTimeFormat(hours, minutes, seconds);
    }

    private String convertTimeFormat(int hours, int minutes, int seconds) {
        String oldFormat = "m:s";
        String newFormat = "mm:ss";
        String stringSongDuration = MessageFormat.format("{0}:{1}", minutes, seconds);
        Log.e("hours", String.valueOf(hours));

        if (hours > 0) {
            oldFormat = "h:m:s";
            newFormat = "hh:mm:ss";
            stringSongDuration = MessageFormat.format("{0}:{1}:{2}", hours, minutes, seconds);
        }
        Log.e("stringSongDuration1", stringSongDuration);

        try {
            Log.e("stringSongDuration2", stringSongDuration);

            stringSongDuration = new SimpleDateFormat(newFormat, Locale.getDefault())
                    .format(new SimpleDateFormat(oldFormat, Locale.getDefault()).parse(stringSongDuration));
            Log.e("stringSongDuration3", stringSongDuration);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Log.e("stringSongDuration4", stringSongDuration);

        return stringSongDuration;
    }
}