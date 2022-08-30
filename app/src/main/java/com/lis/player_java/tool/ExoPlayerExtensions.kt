package com.lis.player_java.tool

import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem


val ExoPlayer.currentMediaItems: List<MediaItem>
    get() {
        return List(mediaItemCount, ::getMediaItemAt)
    }
