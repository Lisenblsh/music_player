package com.lis.player_java.data.Model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Ads {
    private String contentID;
    private String duration;
    private String accountAgeType;
    private String puid1;
    private String puid22;

    @JsonProperty("content_id")
    public String getContentID() {
        return contentID;
    }

    @JsonProperty("content_id")
    public void setContentID(String value) {
        this.contentID = value;
    }

    @JsonProperty("duration")
    public String getDuration() {
        return duration;
    }

    @JsonProperty("duration")
    public void setDuration(String value) {
        this.duration = value;
    }

    @JsonProperty("account_age_type")
    public String getAccountAgeType() {
        return accountAgeType;
    }

    @JsonProperty("account_age_type")
    public void setAccountAgeType(String value) {
        this.accountAgeType = value;
    }

    @JsonProperty("puid1")
    public String getPuid1() {
        return puid1;
    }

    @JsonProperty("puid1")
    public void setPuid1(String value) {
        this.puid1 = value;
    }

    @JsonProperty("puid22")
    public String getPuid22() {
        return puid22;
    }

    @JsonProperty("puid22")
    public void setPuid22(String value) {
        this.puid22 = value;
    }
}
