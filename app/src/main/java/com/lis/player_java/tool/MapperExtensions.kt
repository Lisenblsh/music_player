package com.lis.player_java.tool

import com.lis.player_java.data.model.Item
import com.lis.player_java.data.room.AlbumForMusic
import com.lis.player_java.data.room.GenreType
import com.lis.player_java.data.room.MusicDB

fun List<Item>.convertToMusicDB(): List<MusicDB> {
    return this.map {
        MusicDB(
            it.id,
            it.ownerId,
            "${it.id}_${it.ownerId}",
            it.artist,
            it.title,
            it.url,
            it.album?.thumb?.photo600 ?: "",
            it.album?.thumb?.photo1200 ?: "",
            it.duration,
            it.isExplicit,
            AlbumForMusic(it.id, it.ownerId, it.accessKey),
            it.lyricsId ?: 0,
            GenreType.getGenreById(it.genreId?.toInt() ?: 0)
        )
    }
}