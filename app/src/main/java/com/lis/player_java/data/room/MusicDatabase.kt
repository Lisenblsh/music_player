package com.lis.player_java.data.room

import android.content.Context
import androidx.room.Database
import com.lis.player_java.data.room.model.MusicDB
import com.lis.player_java.data.room.model.AlbumDB
import com.lis.player_java.data.room.model.ArtistDB
import com.lis.player_java.data.room.model.RemoteKeys
import androidx.room.RoomDatabase
import com.lis.player_java.data.room.MusicDao
import com.lis.player_java.data.room.MusicDatabase
import androidx.room.Room

@Database(
    entities = [MusicDB::class, AlbumDB::class, ArtistDB::class, RemoteKeys::class],
    version = 1
)
abstract class MusicDatabase : RoomDatabase() {
    abstract fun musicDao(): MusicDao

    companion object {
        private var INSTANCE: MusicDatabase? = null

        @JvmStatic
        @Synchronized
        fun getInstance(context: Context) =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                MusicDatabase::class.java,
                "music_db"
            ).fallbackToDestructiveMigration().build()
    }
}