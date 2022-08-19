package com.lis.player_java.data.room.model

import androidx.room.Embedded
import androidx.room.Relation

data class MusicArtists(
    @Embedded
    val musicDB: MusicDB,
    @Relation(parentColumn = "id", entityColumn = "musicId")
    val artists: List<ArtistDB>,
    val type: ArtistType
)