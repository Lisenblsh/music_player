package com.lis.player_java.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lis.player_java.R;
import com.lis.player_java.databinding.FragmentMusicListBinding;

public class MusicListFragment extends Fragment {

    private FragmentMusicListBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentMusicListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}