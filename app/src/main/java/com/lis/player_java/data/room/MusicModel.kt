package com.lis.player_java.data.room

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity
data class MusicDB(
    @PrimaryKey val id: Long,
    val ownerId: Long, //ownerID+id
    val contentIs: String,
    val artist: String,
    val title: String,
    val url: String,
    val photo300: String,
    val photo1200: String,
    val duration: Long,
    val isExplicit: Boolean,
    @Embedded val album: AlbumForMusic,
    val lyricsId: Long,
    val genreId: GenreType
)

enum class GenreType(private val id: Int) {
    IDK(0),
    Rock(1),
    Pop(2),
    Rap_Hip_Hop(3),
    Easy_Listening(4),
    House_Dance(5),
    Instrumental(6),
    Metal(7),
    Dubstep(8),
    Drum_Bass(10),
    Trance(11),
    Chanson(12),
    Ethnic(13),
    Acoustic_Vocal(14),
    Reggae(15),
    Classical(16),
    Indie_Pop(17),
    Other(18),
    Speech(19),
    Alternative(21),
    Electropop_Disco(22),
    Jazz_Blues(1001);

    companion object {
        private val map = HashMap<Int, GenreType>()
        fun getGenreById(id: Int): GenreType {
            return map.getOrDefault(id, IDK)
        }

        init {
            for (v in values()) {
                map[v.id] = v
            }
        }
    }
}

data class AlbumForMusic(val albumId: Long, val albumOwnerId: Long, val accessKey: String)

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

@Entity
data class ArtistDB(
    @PrimaryKey
    val id: String = "",
    val musicId: Long,
    val name: String,
    val domain: String
)

enum class ArtistType {
    MAIN, FEATURED
}

data class MusicArtists(
    @Embedded
    val musicDB: MusicDB,
    @Relation(parentColumn = "id", entityColumn = "musicId")
    val artists: List<ArtistDB>,
    val type: ArtistType
)

@Entity
data class RemoteKeys(
    @PrimaryKey val musicId: Long,
    val prefKey: Int?,
    val nextKey: Int?
)