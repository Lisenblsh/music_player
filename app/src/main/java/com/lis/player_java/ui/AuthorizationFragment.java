package com.lis.player_java.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.lis.player_java.R;
import com.lis.player_java.data.network.Authorization;
import com.lis.player_java.databinding.FragmentAuthorizationBinding;

import java.util.Map;

public class AuthorizationFragment extends Fragment {

    private FragmentAuthorizationBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAuthorizationBinding.inflate(inflater, container, false);
        Log.e("onCreate", "author");
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindElement();
    }

    private void bindElement() {
        binding.buttonConfirm.setOnClickListener(v -> confirmListenerClick());
    }

    private void confirmListenerClick() {
        Map<String, String> authInfo = new Authorization().getToken(
                checkLogin(binding.loginEditText.getText().toString()),
                checkPassword(binding.passwordEditText.getText().toString())
        );
        saveAuthInfo(authInfo);
    }

    private void saveAuthInfo(Map<String, String> authInfo) {
        String error = authInfo.getOrDefault(getString(R.string.error), null);
        if (error != null) {
            onException(error);
            return;
        }

        SharedPreferences preferences = requireActivity()
                .getSharedPreferences(getString(R.string.authorization_info), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(
                getString(R.string.token_key),
                authInfo.get(getString(R.string.token_key)));
        editor.putString(getString(R.string.user_agent),
                authInfo.get(getString(R.string.user_agent)));
        editor.apply();
    }

    private void onException(String error) {
        //logic if error from token library
        Toast.makeText(requireContext(), "Ошибка: "+error+"опробуйте в другой раз", Toast.LENGTH_SHORT).show();
    }

    private String checkLogin(String login) {
        return login;
    }

    private String checkPassword(String password) {
        return password;
    }
}
