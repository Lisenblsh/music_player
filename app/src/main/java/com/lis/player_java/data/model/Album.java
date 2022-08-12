package com.lis.player_java.data.model;

import com.google.gson.annotations.SerializedName;

public class Album {
    @SerializedName("id")
    private long id;
    @SerializedName("title")
    private String title;
    @SerializedName("owner_id")
    private long ownerID;
    @SerializedName("access_key")
    private String accessKey;
    @SerializedName("thumb")
    private Thumb thumb;

    public long getID() {
        return id;
    }

    public void setID(long value) {
        this.id = value;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String value) {
        this.title = value;
    }

    public long getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(long value) {
        this.ownerID = value;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String value) {
        this.accessKey = value;
    }

    public Thumb getThumb() {
        return thumb;
    }

    public void setThumb(Thumb value) {
        this.thumb = value;
    }
}
