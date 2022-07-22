package com.lis.player_java.data.Model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Album {
    private long id;
    private String title;
    private long ownerID;
    private String accessKey;
    private Thumb thumb;

    @JsonProperty("id")
    public long getID() {
        return id;
    }

    @JsonProperty("id")
    public void setID(long value) {
        this.id = value;
    }

    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    @JsonProperty("title")
    public void setTitle(String value) {
        this.title = value;
    }

    @JsonProperty("owner_id")
    public long getOwnerID() {
        return ownerID;
    }

    @JsonProperty("owner_id")
    public void setOwnerID(long value) {
        this.ownerID = value;
    }

    @JsonProperty("access_key")
    public String getAccessKey() {
        return accessKey;
    }

    @JsonProperty("access_key")
    public void setAccessKey(String value) {
        this.accessKey = value;
    }

    @JsonProperty("thumb")
    public Thumb getThumb() {
        return thumb;
    }

    @JsonProperty("thumb")
    public void setThumb(Thumb value) {
        this.thumb = value;
    }
}
