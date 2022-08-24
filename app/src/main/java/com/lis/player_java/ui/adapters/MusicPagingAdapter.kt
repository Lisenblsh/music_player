package com.lis.player_java.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.lis.player_java.R
import com.lis.player_java.data.room.MusicDB
import com.lis.player_java.tool.ImageFun

class MusicPagingAdapter : PagingDataAdapter<MusicDB, RecyclerView.ViewHolder>(MUSIC_COMPARISON) {
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val music = getItem(position)
        (holder as MusicViewHolder).bind(music)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.music_card, parent, false)
        return MusicViewHolder(view)
    }

    inner class MusicViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val image = itemView.findViewById<ImageView>(R.id.music_image)
        private val title = itemView.findViewById<TextView>(R.id.title_music)
        private val artistName = itemView.findViewById<TextView>(R.id.artist_name)
        private val menu = itemView.findViewById<ImageView>(R.id.music_menu)

        private var music: MusicDB? = null

        init {

        }

        fun bind(music: MusicDB?) {
            if(music == null){
                TODO("Сюда можно вставить плэйс холдеры при загрузке")
            } else {
                showRepoData(music)
            }
        }

        private fun showRepoData(music: MusicDB) {
            this.music = music

            title.text = music.title
            artistName.text = music.artist
            ImageFun().setImage(image, music.url)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.music_card
    }

    companion object {
        private val MUSIC_COMPARISON = object : DiffUtil.ItemCallback<MusicDB>() {
            override fun areItemsTheSame(oldItem: MusicDB, newItem: MusicDB): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: MusicDB, newItem: MusicDB): Boolean {
                return oldItem == newItem
            }

        }
    }
}