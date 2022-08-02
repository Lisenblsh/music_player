package com.lis.player_java.data.repository;

import com.lis.player_java.R;
import com.lis.player_java.data.Model.VkMusic;
import com.lis.player_java.data.network.retrofit.RetrofitService;

import retrofit2.Call;

public class MusicRepository {

    private RetrofitService service;

    public MusicRepository(RetrofitService service){
        this.service = service;
    }
    public Call<VkMusic> getMusicList(){
        return service.getMusic(
                "***REMOVED***",
                10,
                0
        );
    }
}
