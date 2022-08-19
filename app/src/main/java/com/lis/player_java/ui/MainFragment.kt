package com.lis.player_java.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.lis.player_java.R
import com.lis.player_java.databinding.FragmentMainBinding

class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding
    override fun onStart() {
        super.onStart()
        checkSavedToken()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        showAuthorizationFragment()
        return binding.root
    }

    private fun showAuthorizationFragment() {
        if (checkToken()) {
            val fragmentManager = requireActivity().supportFragmentManager
            val authorizationFragment = AuthorizationFragment()
            authorizationFragment.setStyle(
                DialogFragment.STYLE_NO_TITLE,
                androidx.appcompat.R.style.AlertDialog_AppCompat
            )
            authorizationFragment.show(fragmentManager, "")
        }
    }

    private fun checkToken(): Boolean {
        val pref = requireActivity()
            .getSharedPreferences(getString(R.string.authorization_info), Context.MODE_PRIVATE)
        return pref != null && pref.getString(getString(R.string.token_key), "").isNullOrEmpty()
    }

    private fun checkSavedToken() {
        val pref = requireActivity()
            .getSharedPreferences(getString(R.string.authorization_info), Context.MODE_PRIVATE)
        if (pref != null) {
            if (pref.getString(getString(R.string.token_key), "").isNullOrEmpty()) {
                startFragment(AuthorizationFragment())
            } else {
                startFragment(PlayerFragment())
            }
        }
    }

    private fun startFragment(fragment: Fragment) {
        val directions =
            when (fragment) {
                is AuthorizationFragment -> {
                    MainFragmentDirections.actionMainFragmentToAuthorizationFragment()
                }
                is PlayerFragment -> {
                    MainFragmentDirections.actionMainFragmentToPlayerFragment()
                }
                else -> {
                    return
                }
            }
        NavHostFragment.findNavController(this)
            .navigate(directions)
    }
}