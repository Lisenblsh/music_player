package com.lis.player_java.data.room.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "table_album")
public class AlbumDB {
    @PrimaryKey
    public Long id;
    public Long ownerId;
    public String accessKey;
    public String title;
    public String description;
    public int year;
    public int musicCount;
    public Boolean isFollowing;
    public Boolean isExplicit;
    public String photo300;
    public String photo600;
    public String photo1200;

}
