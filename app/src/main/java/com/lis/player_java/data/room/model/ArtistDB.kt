package com.lis.player_java.data.room.model

import androidx.room.PrimaryKey
import com.lis.player_java.data.room.model.AlbumForMusic
import com.lis.player_java.data.room.model.GenreType
import androidx.room.Embedded
import androidx.room.Entity
import com.lis.player_java.data.room.model.MusicDB
import com.lis.player_java.data.room.model.ArtistDB
import com.lis.player_java.data.room.model.ArtistType

@Entity
data class ArtistDB(
    @PrimaryKey
    val id: String = "",
    val musicId: Long,
    val name: String,
    val domain: String
)