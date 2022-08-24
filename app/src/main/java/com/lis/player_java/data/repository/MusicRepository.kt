package com.lis.player_java.data.repository

import com.lis.player_java.data.network.retrofit.RetrofitService

class MusicRepository(private val service: RetrofitService, private val token: String) {
    suspend fun getMusicList(
        count: Int,
        offset: Int,
        ownerId: Long? = null,
        albumId: Long? = null,
        accessKey: String? = null
    ) = service.getAudio(token, count, offset, ownerId, albumId, accessKey)
}