package com.lis.player_java.data.Model;

import com.google.gson.annotations.SerializedName;

public class Artist {
    @SerializedName("name")
    private String name;
    @SerializedName("domain")
    private String domain;
    @SerializedName("id")
    private String id;

    public String getName() {
        return name;
    }

    public void setName(String value) {
        this.name = value;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String value) {
        this.domain = value;
    }

    public String getID() {
        return id;
    }

    public void setID(String value) {
        this.id = value;
    }
}
