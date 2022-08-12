package com.lis.player_java.data.network.retrofit;

import com.lis.player_java.data.model.VkMusic;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitService {
    @GET("audio.get?v=5.95")
    public Call<VkMusic> getMusic(
            @Query("access_token") String accessToken,
            @Query("count") int count,
            @Query("offset") int offset);
}
