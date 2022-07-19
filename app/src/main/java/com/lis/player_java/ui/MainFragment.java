package com.lis.player_java.ui;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;

import com.lis.player_java.R;
import com.lis.player_java.databinding.FragmentMainBinding;

import java.util.Objects;

public class MainFragment extends Fragment {

    private FragmentMainBinding binding;

    @Override
    public void onStart() {
        super.onStart();
        checkSavedToken();
    }

    private void checkSavedToken() {
        SharedPreferences pref = requireActivity()
                .getSharedPreferences(getString(R.string.authorization_info), Context.MODE_PRIVATE);
        if (pref != null) {
            if (pref.getString(getString(R.string.token_key), "").equals("")) {
                startFragment(new AuthorizationFragment());
            } else {
                startFragment(new PlayerFragment());
            }

        }
    }


    private void startFragment(Fragment fragment) {
        @NonNull NavDirections directions;
        if (fragment instanceof AuthorizationFragment) {
            directions = MainFragmentDirections.actionMainFragmentToAuthorizationFragment();
        } else if (fragment instanceof PlayerFragment) {
            directions = MainFragmentDirections.actionMainFragmentToPlayerFragment();
        } else {
            return;
        }
        NavHostFragment
                .findNavController(this)
                .navigate(directions);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMainBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

}