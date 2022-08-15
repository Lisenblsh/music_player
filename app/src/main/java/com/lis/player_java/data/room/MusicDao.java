package com.lis.player_java.data.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.lis.player_java.data.room.model.AlbumDB;
import com.lis.player_java.data.room.model.ArtistDB;
import com.lis.player_java.data.room.model.MusicDB;
import com.lis.player_java.data.room.model.RemoteKeys;

import java.util.List;

@Dao
public interface MusicDao {

    //AlbumBD
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAlbum(AlbumDB albumDB);

    @Transaction
    @Query("SELECT * FROM AlbumDB")
    AlbumDB getAlbums();

    @Transaction
    @Query("SELECT * FROM AlbumDB WHERE id = :id")
    AlbumDB getAlbumById(Long id);

    @Transaction
    @Query("DELETE FROM AlbumDB WHERE id = :id")
    void deleteAlbumById(Long id);

    //MusicDB
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMusic(MusicDB musicDB);

    @Transaction
    @Query("SELECT * FROM MusicDB")
    MusicDB getMusic();

    @Transaction
    @Query("SELECT * FROM MusicDB WHERE id = :id")
    MusicDB getMusicById(Long id);

    @Transaction
    @Query("DELETE FROM MusicDB WHERE id = :id")
    void deleteMusicById(Long id);

    //ArtistDB
    @Insert
    void insertArtist(List<ArtistDB> artists);

    @Transaction
    @Query("SELECT * FROM ArtistDB")
    ArtistDB getArtist();

    @Transaction
    @Query("SELECT * FROM ArtistDB WHERE id = :id")
    ArtistDB getArtistById(String id);

    @Transaction
    @Query("DELETE FROM ArtistDB WHERE id = :id")
    void deleteArtistById(String id);

    //RemoteKeys
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllKeys(List<RemoteKeys> remoteKeys);

    @Transaction
    @Query("SELECT * FROM RemoteKeys WHERE musicId = :musicId")
    RemoteKeys getRemoteKey(Long musicId);

    @Transaction
    @Query("DELETE FROM ArtistDB")
    void clearRemoteKeysTable();
}
