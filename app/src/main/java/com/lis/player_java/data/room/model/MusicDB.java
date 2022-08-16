package com.lis.player_java.data.room.model;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class MusicDB {
    @PrimaryKey
    public Long id;
    public Long ownerId;
    public String contentIs; //ownerID+id
    public String artist;
    public String title;
    public String url;
    public String photo300;
    public String photo600;
    public long duration;
    public boolean isExplicit;
    @Embedded
    public AlbumForMusic album;
    public Long lyricsId;
    public GenreType genreId;

    public MusicDB(Long id, Long ownerId, String contentIs, String artist, String title, String url,
                   String photo300, String photo600, long duration, boolean isExplicit,
                   AlbumForMusic album, Long lyricsId, GenreType genreId) {
        this.id = id;
        this.ownerId = ownerId;
        this.contentIs = contentIs;
        this.artist = artist;
        this.title = title;
        this.url = url;
        this.photo300 = photo300;
        this.photo600 = photo600;
        this.duration = duration;
        this.isExplicit = isExplicit;
        this.album = album;
        this.lyricsId = lyricsId;
        this.genreId = genreId;
    }
}


