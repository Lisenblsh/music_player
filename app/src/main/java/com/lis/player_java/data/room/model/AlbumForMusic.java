package com.lis.player_java.data.room.model;

public class AlbumForMusic {
    public Long albumId;
    public Long albumOwnerId;
    public String accessKey;

    AlbumForMusic(Long albumId, Long albumOwnerId, String accessKey){
        this.albumId = albumId;
        this.albumOwnerId = albumOwnerId;
        this.accessKey = accessKey;
    }
}
