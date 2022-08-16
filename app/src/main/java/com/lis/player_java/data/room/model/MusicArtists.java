package com.lis.player_java.data.room.model;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class MusicArtists {
    @Embedded public MusicDB musicDB;
    @Relation(
            parentColumn = "id",
            entityColumn = "musicId"
    )
    public List<ArtistDB> artists;
    public ArtistType type;
}
