package com.lis.player_java.data.room.model

import androidx.room.PrimaryKey
import com.lis.player_java.data.room.model.AlbumForMusic
import com.lis.player_java.data.room.model.GenreType
import androidx.room.Embedded
import com.lis.player_java.data.room.model.MusicDB
import com.lis.player_java.data.room.model.ArtistDB
import com.lis.player_java.data.room.model.ArtistType

enum class ArtistType {
    MAIN, FEATURED
}