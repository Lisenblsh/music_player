package com.lis.player_java.data.room.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;
import com.lis.player_java.data.model.Ads;
import com.lis.player_java.data.model.Album;
import com.lis.player_java.data.model.Artist;
import com.lis.player_java.data.room.GenreType;

@Entity(tableName = "table_music")
public class MusicDB {
    @PrimaryKey
    public Long id;
    public String artist;
    public String title;
    public long duration;
    public String contentID; //id + ownerID
    public boolean isExplicit;
    public String url;
    public boolean isHq;
    public Album album;
    public Artist[] mainArtists;
    public Artist[] featuredArtists;
    public Long lyricsID;
    public GenreType genreID;
}

