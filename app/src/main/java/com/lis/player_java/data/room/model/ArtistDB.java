package com.lis.player_java.data.room.model;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "table_artist",
        foreignKeys =
                {@ForeignKey(entity = MusicDB.class,
                    parentColumns = "mainArtists",
                        childColumns = "id",
                        onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = MusicDB.class,
                        parentColumns = "featuredArtists",
                        childColumns = "id",
                        onDelete = ForeignKey.CASCADE)})
public class ArtistDB {
    @PrimaryKey
    public String id;
    public String name;
    public String domain;
}