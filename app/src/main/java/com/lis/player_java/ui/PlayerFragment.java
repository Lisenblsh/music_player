package com.lis.player_java.ui;

import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.lis.player_java.ImageFun;
import com.lis.player_java.R;
import com.lis.player_java.databinding.FragmentPlayerBinding;
import com.lis.player_java.di.Injection;
import com.lis.player_java.viewModel.PlaybackViewModel;

import java.text.MessageFormat;
import java.text.ParseException;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class PlayerFragment extends Fragment {
    private FragmentPlayerBinding binding;
    private PlaybackViewModel viewModel;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentPlayerBinding.inflate(inflater,container,false);
        viewModel = new ViewModelProvider(
                this, Injection.INSTANCE.provideViewModelFactory(requireActivity().getApplication())
        ).get(PlaybackViewModel.class);

        viewModel.setupMediaPlayer(R.raw.music);

        bindElement();
        return binding.getRoot();
    }


    private void bindElement() {
        onStartNewSound();

        viewModel.getPosition().observe(getViewLifecycleOwner(), position -> {
            binding.songPosition.setText(getStringSongDuration(position.longValue()));
            binding.songProgress.setProgress(position.intValue());
        });

        viewModel.isPlaying().observe(getViewLifecycleOwner(), isPlaying -> {
            new ImageFun().setImage(binding.buttonPlayPause,
                    isPlaying ? R.drawable.ic_baseline_pause_24
                            : R.drawable.ic_baseline_play_arrow_24);

        });

        binding.songProgress.setOnSeekBarChangeListener(seekBarSelectProgressListener());

        binding.buttonPlayPause.setOnClickListener(v -> startClickListener());

            binding.buttonNext.setOnClickListener(v -> nextClickListener());
            binding.buttonPrevious.setOnClickListener(v -> prevClickListener());

            binding.buttonLoop.setOnClickListener(this::loopClickListener);
    }

    private Boolean isLooping = false;

    private void loopClickListener(View v) {
        isLooping = !isLooping;
        v.setAlpha(isLooping ? 1 : 0.5f);
        viewModel.setLooping(isLooping);
        Log.e("asda", ""+viewModel.isLooping().getValue());
    }

    private void onStartNewSound() {

        viewModel.getDuration().observe(getViewLifecycleOwner(), songDuration -> {
            binding.songProgress.setMax(songDuration.intValue());
            binding.songDuration.setText(getStringSongDuration(songDuration.longValue()));
        });

    }


    private void startClickListener() {
        boolean isPlaying = Boolean.TRUE.equals(viewModel.isPlaying().getValue());
        if (isPlaying) {
            viewModel.pause();
       } else {
            viewModel.start();
        }
    }

    private void nextClickListener() {
        viewModel.nextSong();
    }

    private void prevClickListener() {
        viewModel.prevSong();
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
                viewModel.seekTo(seekBar.getProgress());
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

        if (hours > 0) {
            oldFormat = "h:m:s";
            newFormat = "hh:mm:ss";
            stringSongDuration = MessageFormat.format("{0}:{1}:{2}", hours, minutes, seconds);
        }

        try {
            stringSongDuration = new SimpleDateFormat(newFormat, Locale.getDefault())
                    .format(new SimpleDateFormat(oldFormat, Locale.getDefault()).parse(stringSongDuration));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return stringSongDuration;
    }
}
