package com.lis.player_java.data.model;

import com.google.gson.annotations.SerializedName;

public class Thumb {
    @SerializedName("width")
    private long width;
    @SerializedName("height")
    private long height;
    @SerializedName("photo_34")
    private String photo34;
    @SerializedName("photo_68")
    private String photo68;
    @SerializedName("photo_135")
    private String photo135;
    @SerializedName("photo_270")
    private String photo270;
    @SerializedName("photo_300")
    private String photo300;
    @SerializedName("photo_600")
    private String photo600;
    @SerializedName("photo_1200")
    private String photo1200;

    public long getWidth() {
        return width;
    }

    public void setWidth(long value) {
        this.width = value;
    }

    public long getHeight() {
        return height;
    }

    public void setHeight(long value) {
        this.height = value;
    }

    public String getPhoto34() {
        return photo34;
    }

    public void setPhoto34(String value) {
        this.photo34 = value;
    }

    public String getPhoto68() {
        return photo68;
    }

    public void setPhoto68(String value) {
        this.photo68 = value;
    }

    public String getPhoto135() {
        return photo135;
    }

    public void setPhoto135(String value) {
        this.photo135 = value;
    }

    public String getPhoto270() {
        return photo270;
    }

    public void setPhoto270(String value) {
        this.photo270 = value;
    }

    public String getPhoto300() {
        return photo300;
    }

    public void setPhoto300(String value) {
        this.photo300 = value;
    }

    public String getPhoto600() {
        return photo600;
    }

    public void setPhoto600(String value) {
        this.photo600 = value;
    }

    public String getPhoto1200() {
        return photo1200;
    }

    public void setPhoto1200(String value) {
        this.photo1200 = value;
    }
}
