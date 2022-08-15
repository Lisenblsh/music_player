package com.lis.player_java.data.room.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class AlbumDB {
    @PrimaryKey
    public Long id;
    public Long ownerId;
    public String accessKey;
    public String title;
    public int year;
    public int musicCount;
    public Boolean isFollowing;
    public Boolean isExplicit;
    public String photo600;
    public String photo1200;

    public AlbumDB(Long id, Long ownerId, String accessKey, String title, int year, int musicCount,
                   boolean isFollowing, boolean isExplicit, String photo600, String photo1200) {
        this.id = id;
        this.ownerId = ownerId;
        this.accessKey = accessKey;
        this.title = title;
        this.year = year;
        this.musicCount = musicCount;
        this.isFollowing = isFollowing;
        this.isExplicit = isExplicit;
        this.photo600 = photo600;
        this.photo1200 = photo1200;
    }
}
