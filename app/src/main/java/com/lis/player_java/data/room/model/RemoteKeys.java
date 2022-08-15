package com.lis.player_java.data.room.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class RemoteKeys {
    @PrimaryKey
    public Long musicId;
    public int prefKey;
    public int nextKey;

    public RemoteKeys(Long musicId, int prefKey, int nextKey) {
        this.musicId = musicId;
        this.prefKey = prefKey;
        this.nextKey = nextKey;
    }
}
