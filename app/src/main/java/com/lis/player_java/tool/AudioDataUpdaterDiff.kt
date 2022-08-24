package com.lis.player_java.tool

import com.google.android.exoplayer2.ExoPlayer
import com.lis.player_java.data.room.MusicDB

interface AudioDataUpdaterDiff {
    suspend fun update(player: ExoPlayer, newData: List<MusicDB>)
}