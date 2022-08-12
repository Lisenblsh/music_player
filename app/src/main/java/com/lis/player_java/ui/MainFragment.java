package com.lis.player_java.ui;

import static androidx.fragment.app.DialogFragment.STYLE_NO_TITLE;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;

import com.lis.player_java.R;
import com.lis.player_java.databinding.FragmentMainBinding;

public class MainFragment extends Fragment {

    private FragmentMainBinding binding;

    @Override
    public void onStart() {
        super.onStart();
        checkSavedToken();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMainBinding.inflate(inflater, container, false);
        showAuthorizationFragment();
        return binding.getRoot();
    }

    private void showAuthorizationFragment() {
        if(checkToken()){
            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            AuthorizationFragment authorizationFragment = new AuthorizationFragment();
            authorizationFragment.setStyle(
                    DialogFragment.STYLE_NO_TITLE,
                    androidx.appcompat.R.style.AlertDialog_AppCompat
            );
            authorizationFragment.show(fragmentManager, "");
        }
    }

    private Boolean checkToken() {

        SharedPreferences pref = requireActivity()
                .getSharedPreferences(getString(R.string.authorization_info), Context.MODE_PRIVATE);

        return pref != null && pref.getString(getString(R.string.token_key), "").equals("");
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

}