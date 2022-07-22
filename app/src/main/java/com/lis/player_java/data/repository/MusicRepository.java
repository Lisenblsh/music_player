package com.lis.player_java.data.repository;

import com.lis.player_java.R;
import com.lis.player_java.data.network.retrofit.RetrofitService;

public class MusicRepository {

    private RetrofitService service;

    public MusicRepository(RetrofitService service){
        this.service = service;
    }
    public int[] getMusicList(){
        return new int[] {R.raw.music,R.raw.music2,R.raw.music3};
    }
}
