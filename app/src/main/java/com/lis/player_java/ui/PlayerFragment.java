package com.lis.player_java.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.exoplayer2.Player;
import com.lis.player_java.R;
import com.lis.player_java.data.model.Thumb;
import com.lis.player_java.databinding.FragmentPlayerBinding;
import com.lis.player_java.di.Injection;
import com.lis.player_java.tool.ImageFun;
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
        binding = FragmentPlayerBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(
                this, getFactory()
        ).get(PlaybackViewModel.class);

        bindElement();
        return binding.getRoot();
    }


    private void bindElement() {
        viewModel.getDuration().observe(getViewLifecycleOwner(), songDuration -> {
            Toast.makeText(requireContext(), ""+songDuration, Toast.LENGTH_SHORT).show();
            binding.songProgress.setMax(songDuration.intValue());
            binding.songDuration.setText(getStringSongDuration(songDuration));
        });

        ImageFun imageFun = new ImageFun();

        viewModel.getMusicInfo().observe(getViewLifecycleOwner(), musicInfo -> {
            if(musicInfo != null){
                Thumb thumb = null;
                try {
                    thumb = musicInfo.getAlbum().getThumb();

                } catch (NullPointerException e){
                    Log.e("info", Objects.toString(musicInfo));
                }

                if(thumb != null){
                    String image = thumb.getPhoto600();
                    imageFun.setImageToBackground(binding.backgroundImage, image);
                    imageFun.setImage(binding.songImage, image);
                } else {
                    //для установки дефолтной картинки
                    imageFun.setImageToBackground(binding.backgroundImage, R.drawable.song_image);
                    imageFun.setImage(binding.songImage, R.drawable.song_image);
                }

                binding.songName.setText(musicInfo.getTitle());
                binding.songAuthor.setText(musicInfo.getArtist());
            }
        });//заполнение сведений о песне

        viewModel.getPosition().observe(getViewLifecycleOwner(), position -> {
            binding.songPosition.setText(getStringSongDuration(position.longValue()));
            binding.songProgress.setProgress(position.intValue());
        });//установка текущей позиции

        viewModel.getDownloadPosition().observe(getViewLifecycleOwner(), downloadPosition -> {
            int progress;
            if(downloadPosition != 0){
                progress = (int) (binding.songProgress.getMax() * downloadPosition / 100);
            } else {
                progress = 0;
            }
            if(binding.songProgress.getSecondaryProgress() != progress){
                binding.songProgress.setSecondaryProgress(progress);
                //Log.e("download", ""+progress+"|"+downloadPosition);
            }

        });

        viewModel.isPlaying().observe(getViewLifecycleOwner(), isPlaying -> {
            imageFun.setImage(binding.buttonPlayPause,
                    isPlaying ? R.drawable.ic_baseline_pause_24
                            : R.drawable.ic_baseline_play_arrow_24);

        });//установка иконки play\pause кнопки

        viewModel.getRepeatMode().observe(getViewLifecycleOwner(), repeatMode -> {
            switch (repeatMode) {
                case Player.REPEAT_MODE_OFF:
                    imageFun.setImage(binding.buttonLoop, R.drawable.ic_baseline_plus_one_24);
                    break;
                case Player.REPEAT_MODE_ONE:
                    imageFun.setImage(binding.buttonLoop, R.drawable.ic_baseline_loop_24);
                    break;
                case Player.REPEAT_MODE_ALL:
                    imageFun.setImage(binding.buttonLoop, R.drawable.ic_baseline_low_priority_24);
                    break;
            }
        });// установка иконки зацикливания

        viewModel.getCount().observe(getViewLifecycleOwner(), count -> {
            Toast.makeText(requireContext(), ""+ count, Toast.LENGTH_SHORT).show();
        });

        binding.songProgress.setOnSeekBarChangeListener(seekBarSelectProgressListener());

        binding.buttonPlayPause.setOnClickListener(v -> startClickListener());

        binding.buttonNext.setOnClickListener(v -> nextClickListener());
        binding.buttonPrevious.setOnClickListener(v -> prevClickListener());

        binding.buttonLoop.setOnClickListener(this::loopClickListener);
    }
    private int repeatMode = Player.REPEAT_MODE_OFF;

    private void loopClickListener(View v) {
        switch (repeatMode) {
            case Player.REPEAT_MODE_OFF:
                repeatMode = Player.REPEAT_MODE_ONE;
                break;
            case Player.REPEAT_MODE_ONE:
                repeatMode = Player.REPEAT_MODE_ALL;
                break;
            case Player.REPEAT_MODE_ALL:
                repeatMode = Player.REPEAT_MODE_OFF;
                break;
        }
        viewModel.setLoopingState(repeatMode);
    }


    private void startClickListener() {
        boolean isPlaying = Boolean.TRUE.equals(viewModel.isPlaying().getValue());
        Log.e("isPlaying", ""+isPlaying);
        if (isPlaying) {
            viewModel.pause();
        } else {
            viewModel.play();
        }
        isPlaying = Boolean.TRUE.equals(viewModel.isPlaying().getValue());
        Log.e("isPlaying1", ""+isPlaying);
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

    private SharedPreferences getPreference() {
        return requireActivity().getSharedPreferences(getString(R.string.authorization_info), Context.MODE_PRIVATE);
    }

    private ViewModelProvider.Factory getFactory() {
        String userAgent = getPreference().getString(getResources().getString(R.string.user_agent), "");
        String token = getPreference().getString(getResources().getString(R.string.token_key),"");
        //Добавть проерку userAgent ибо малоли что может произойти перебздеть стоит.
        return Injection.INSTANCE.provideViewModelFactory(userAgent,token,requireContext());
    }
}
