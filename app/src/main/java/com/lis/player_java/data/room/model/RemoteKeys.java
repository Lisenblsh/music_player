package com.lis.player_java.data.room.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "remote_keys")
public class RemoteKeys {
    @PrimaryKey
    public Long musicId;
    public int prefKey;
    public int nextKey;
}
