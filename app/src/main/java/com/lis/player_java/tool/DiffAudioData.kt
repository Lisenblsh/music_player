package com.lis.player_java.tool

import com.github.difflib.DiffUtils
import com.github.difflib.patch.AbstractDelta
import com.github.difflib.patch.DeltaType
import com.github.difflib.patch.Patch
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.lis.player_java.data.room.MusicDB
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class DiffAudioData(private val context: CoroutineContext, private val player: ExoPlayer) : AudioDataUpdaterDiff {

    override suspend fun update(incoming: List<MusicDB>) {
        val oldData = player.currentMediaItems
        val newData = incoming.toMediaItems()

        val patch: Patch<MediaItem> = withContext(context) {
            DiffUtils.diff(oldData, newData)
        }

        patch.deltas.forEach { delta ->
            when (delta.type) {
                DeltaType.CHANGE -> {
                    delta.delete(player)
                    delta.insert(player)
                }
                DeltaType.DELETE -> {
                    delta.delete(player)
                }
                DeltaType.INSERT -> {
                    delta.insert(player)
                }
                DeltaType.EQUAL -> {}
                null -> {}
            }
        }
    }

    override suspend fun add(incoming: List<MusicDB>) {
        player.addMediaItems(incoming.toMediaItems())
    }

    private fun AbstractDelta<MediaItem>.delete(player: ExoPlayer) {
        player.removeMediaItems(target.position, target.position + source.lines.size)
    }

    private fun AbstractDelta<MediaItem>.insert(player: ExoPlayer) {
        player.addMediaItems(target.position, target.lines)
    }

    private fun List<MusicDB>.toMediaItems(): List<MediaItem> =
        map { data ->
            MediaItem.Builder().setUri(data.url).setMediaId(data.id.toString()).build()
        }


}

val ExoPlayer.currentMediaItems: List<MediaItem>
    get() {
        return List(mediaItemCount, ::getMediaItemAt)
    }