package com.lis.player_java.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lis.player_java.R
import com.lis.player_java.databinding.FragmentMusicListBinding
import com.lis.player_java.di.Injection
import com.lis.player_java.ui.adapters.MusicPagingAdapter
import com.lis.player_java.viewModel.MusicListViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MusicListFragment : Fragment() {
    private lateinit var binding: FragmentMusicListBinding

    private lateinit var viewModel: MusicListViewModel

    private val musicAdapter = MusicPagingAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.e("statrMusicListFragment", "started")
        if(!this::binding.isInitialized){
            binding = FragmentMusicListBinding.inflate(inflater, container, false)
            viewModel = ViewModelProvider(this, factory)[MusicListViewModel::class.java]
            binding.bindElement()
        }
        return binding.root
    }

    private fun FragmentMusicListBinding.bindElement(){
        musicList.adapter = musicAdapter
        musicList.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)

        lifecycleScope.launch {
            viewModel.pagingMusicList.collectLatest(musicAdapter::submitData)
        }
    }

    private val preference: SharedPreferences
        get() = requireActivity().getSharedPreferences(
            getString(R.string.authorization_info),
            Context.MODE_PRIVATE
        )

    //Добавть проерку userAgent ибо малоли что может произойти перебздеть стоит.
    private val factory: ViewModelProvider.Factory
        get() {
            val userAgent = preference.getString(resources.getString(R.string.user_agent), "")?: ""
            val token = preference.getString(resources.getString(R.string.token_key), "")?: ""
            //Добавть проерку userAgent ибо малоли что может произойти перебздеть стоит.
            return Injection.provideViewModelFactory(userAgent, token, requireContext())
        }
}