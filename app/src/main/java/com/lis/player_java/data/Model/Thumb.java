package com.lis.player_java.data.Model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Thumb {
    private long width;
    private long height;
    private String photo34;
    private String photo68;
    private String photo135;
    private String photo270;
    private String photo300;
    private String photo600;
    private String photo1200;

    @JsonProperty("width")
    public long getWidth() {
        return width;
    }

    @JsonProperty("width")
    public void setWidth(long value) {
        this.width = value;
    }

    @JsonProperty("height")
    public long getHeight() {
        return height;
    }

    @JsonProperty("height")
    public void setHeight(long value) {
        this.height = value;
    }

    @JsonProperty("photo_34")
    public String getPhoto34() {
        return photo34;
    }

    @JsonProperty("photo_34")
    public void setPhoto34(String value) {
        this.photo34 = value;
    }

    @JsonProperty("photo_68")
    public String getPhoto68() {
        return photo68;
    }

    @JsonProperty("photo_68")
    public void setPhoto68(String value) {
        this.photo68 = value;
    }

    @JsonProperty("photo_135")
    public String getPhoto135() {
        return photo135;
    }

    @JsonProperty("photo_135")
    public void setPhoto135(String value) {
        this.photo135 = value;
    }

    @JsonProperty("photo_270")
    public String getPhoto270() {
        return photo270;
    }

    @JsonProperty("photo_270")
    public void setPhoto270(String value) {
        this.photo270 = value;
    }

    @JsonProperty("photo_300")
    public String getPhoto300() {
        return photo300;
    }

    @JsonProperty("photo_300")
    public void setPhoto300(String value) {
        this.photo300 = value;
    }

    @JsonProperty("photo_600")
    public String getPhoto600() {
        return photo600;
    }

    @JsonProperty("photo_600")
    public void setPhoto600(String value) {
        this.photo600 = value;
    }

    @JsonProperty("photo_1200")
    public String getPhoto1200() {
        return photo1200;
    }

    @JsonProperty("photo_1200")
    public void setPhoto1200(String value) {
        this.photo1200 = value;
    }
}
