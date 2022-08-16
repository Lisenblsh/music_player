package com.lis.player_java.data.repository;

import com.lis.player_java.data.model.VkMusic;
import com.lis.player_java.data.network.retrofit.RetrofitService;

import retrofit2.Call;

public class MusicRepository {

    private final RetrofitService service;
    private final String token;

    public MusicRepository(RetrofitService service, String token){
        this.service = service;
        this.token = token;
    }
    public Call<VkMusic> getMusicList(Integer count, Integer offset){
        return service.getMusic(
                token,
                count,
                offset
        );
    }
}
