package com.lis.player_java.data.room.model;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;

@Entity
public class ArtistDB {
    @PrimaryKey
    @NotNull
    public String id;
    public Long musicId;
    public String name;
    public String domain;

    ArtistDB(String id, Long musicId, String name, String domain) {
        this.id = id != null ? id : "";
        this.musicId = musicId;
        this.name = name;
        this.domain = domain;
    }

}