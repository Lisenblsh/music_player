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
data class AlbumDB(
    @PrimaryKey val id: Long,
    val ownerId: Long,
    val accessKey: String,
    val title: String,
    val year: Int,
    val musicCount: Int,
    val isFollowing: Boolean,
    val isExplicit: Boolean,
    val photo300: String,
    val photo600: String
)