package com.lis.player_java.data.room

import androidx.paging.PagingSource
import androidx.room.*

@Dao
interface MusicDao {
    //AlbumBD
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAlbum(albumDB: AlbumDB)

    @Query("SELECT * FROM AlbumDB")
    @Transaction
    suspend fun getAlbums(): AlbumDB?

    @Transaction
    @Query("SELECT * FROM AlbumDB WHERE id = :id")
    fun getAlbumById(id: Long): AlbumDB?

    @Transaction
    @Query("DELETE FROM AlbumDB WHERE id = :id")
    fun deleteAlbumById(id: Long)

    //MusicDB
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMusic(musicDB: MusicDB)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMusicList(musics: List<MusicDB>)

    @Query("SELECT * FROM MusicDB")
    @Transaction
    suspend fun getMusicList(): List<MusicDB>

    @Query("SELECT * FROM MusicDB")
    @Transaction
    fun getPagingMusicList(): PagingSource<Int, MusicDB>

    @Transaction
    @Query("SELECT * FROM MusicDB WHERE id = :id")
    fun getMusicById(id: Long): MusicDB

    @Transaction
    @Query("DELETE FROM MusicDB WHERE id = :id")
    fun deleteMusicById(id: Long)

    @Transaction
    @Query("DELETE FROM MusicDB")
    fun deleteMusic()

    //ArtistDB
    @Insert
    fun insertArtist(artists: List<ArtistDB>)

    @Query("SELECT * FROM ArtistDB")
    @Transaction
    suspend fun getArtist(): ArtistDB?

    @Transaction
    @Query("SELECT * FROM ArtistDB WHERE id = :id")
    fun getArtistById(id: String): ArtistDB?

    @Transaction
    @Query("DELETE FROM ArtistDB WHERE id = :id")
    fun deleteArtistById(id: String)

    //RemoteKeys
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllKeys(remoteKeys: List<RemoteKeys>)

    @Transaction
    @Query("SELECT * FROM RemoteKeys WHERE musicId = :musicId")
    fun getRemoteKey(musicId: Long): RemoteKeys?

    @Transaction
    @Query("DELETE FROM ArtistDB")
    fun clearRemoteKeysTable()
}