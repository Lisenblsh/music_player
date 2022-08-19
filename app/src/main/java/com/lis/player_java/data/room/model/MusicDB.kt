package com.lis.player_java.data.room.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MusicDB(
    @PrimaryKey val id: Long,
    val ownerId: Long, //ownerID+id
    val contentIs: String,
    val artist: String,
    val title: String,
    val url: String,
    val photo600: String,
    val photo1200: String,
    val duration: Long,
    val isExplicit: Boolean,
    @Embedded val album: AlbumForMusic,
    val lyricsId: Long,
    val genreId: GenreType
)