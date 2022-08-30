package com.lis.player_java.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [MusicDB::class, AlbumDB::class, ArtistDB::class, RemoteKeys::class],
    version = 2
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