package com.lis.player_java.data.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.lis.player_java.data.room.model.AlbumDB;
import com.lis.player_java.data.room.model.ArtistDB;
import com.lis.player_java.data.room.model.MusicDB;
import com.lis.player_java.data.room.model.RemoteKeys;

@Database(entities = {MusicDB.class, AlbumDB.class,ArtistDB.class, RemoteKeys.class}, version = 1)
public abstract class MusicDatabase extends RoomDatabase {
    public abstract MusicDao musicDao();

    private static MusicDatabase INSTANCE;

    public static synchronized MusicDatabase getInstance(Context context) {
        if(INSTANCE == null) {
            INSTANCE =Room.databaseBuilder(context.getApplicationContext(),
                            MusicDatabase.class,
                            "music_db")
                    .fallbackToDestructiveMigration().build();
        }
        return INSTANCE;
    }
}
