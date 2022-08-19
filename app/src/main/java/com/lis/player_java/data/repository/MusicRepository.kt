package com.lis.player_java.data.repository

import com.lis.player_java.data.model.VkMusic
import com.lis.player_java.data.network.retrofit.RetrofitService
import retrofit2.Call
import retrofit2.Response

class MusicRepository(private val service: RetrofitService, private val token: String) {
    suspend fun getMusicList(count: Int, offset: Int): Response<VkMusic> {
        return service.getMusic(
            token,
            count,
            offset
        )
    }

}