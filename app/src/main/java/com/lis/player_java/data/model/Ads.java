package com.lis.player_java.data.model;

import com.google.gson.annotations.SerializedName;

public class Ads {
    @SerializedName("content_id")
    private String contentID;
    @SerializedName("duration")
    private String duration;
    @SerializedName("account_age_type")
    private String accountAgeType;
    @SerializedName("puid1")
    private String puid1;
    @SerializedName("puid22")
    private String puid22;

    public String getContentID() {
        return contentID;
    }

    public void setContentID(String value) {
        this.contentID = value;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String value) {
        this.duration = value;
    }

    public String getAccountAgeType() {
        return accountAgeType;
    }

    public void setAccountAgeType(String value) {
        this.accountAgeType = value;
    }

    public String getPuid1() {
        return puid1;
    }

    public void setPuid1(String value) {
        this.puid1 = value;
    }

    public String getPuid22() {
        return puid22;
    }

    public void setPuid22(String value) {
        this.puid22 = value;
    }
}
