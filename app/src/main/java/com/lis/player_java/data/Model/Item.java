package com.lis.player_java.data.Model;

import com.google.gson.annotations.SerializedName;

public class Item {
    @SerializedName("artist")
    private String artist;
    @SerializedName("id")
    private long id;
    @SerializedName("owner_id")
    private long ownerID;
    @SerializedName("title")
    private String title;
    @SerializedName("duration")
    private long duration;
    @SerializedName("access_key")
    private String accessKey;
    @SerializedName("ads")
    private Ads ads;
    @SerializedName("is_explicit")
    private boolean isExplicit;
    @SerializedName("is_licensed")
    private boolean isLicensed;
    @SerializedName("track_code")
    private String trackCode;
    @SerializedName("url")
    private String url;
    @SerializedName("date")
    private long date;
    @SerializedName("is_hq")
    private boolean isHq;
    @SerializedName("album")
    private Album album;
    @SerializedName("main_artists")
    private Artist[] mainArtists;
    @SerializedName("featured_artists")
    private Artist[] featuredArtists;
    @SerializedName("short_videos_allowed")
    private boolean shortVideosAllowed;
    @SerializedName("stories_allowed")
    private boolean storiesAllowed;
    @SerializedName("stories_cover_allowed")
    private boolean storiesCoverAllowed;
    @SerializedName("lyrics_id")
    private Long lyricsID;
    @SerializedName("genre_id")
    private Long genreID;

    public String getArtist() {
        return artist;
    }

    public void setArtist(String value) {
        this.artist = value;
    }

    public long getID() {
        return id;
    }

    public void setID(long value) {
        this.id = value;
    }

    public long getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(long value) {
        this.ownerID = value;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String value) {
        this.title = value;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long value) {
        this.duration = value;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String value) {
        this.accessKey = value;
    }

    public Ads getAds() {
        return ads;
    }

    public void setAds(Ads value) {
        this.ads = value;
    }

    public boolean getIsExplicit() {
        return isExplicit;
    }

    public void setIsExplicit(boolean value) {
        this.isExplicit = value;
    }

    public boolean getIsLicensed() {
        return isLicensed;
    }

    public void setIsLicensed(boolean value) {
        this.isLicensed = value;
    }

    public String getTrackCode() {
        return trackCode;
    }

    public void setTrackCode(String value) {
        this.trackCode = value;
    }

    public String getURL() {
        return url;
    }

    public void setURL(String value) {
        this.url = value;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long value) {
        this.date = value;
    }

    public boolean getIsHq() {
        return isHq;
    }

    public void setIsHq(boolean value) {
        this.isHq = value;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album value) {
        this.album = value;
    }

    public Artist[] getMainArtists() {
        return mainArtists;
    }

    public void setMainArtists(Artist[] value) {
        this.mainArtists = value;
    }

    public Artist[] getFeaturedArtists() {
        return featuredArtists;
    }

    public void setFeaturedArtists(Artist[] value) {
        this.featuredArtists = value;
    }

    public boolean getShortVideosAllowed() {
        return shortVideosAllowed;
    }

    public void setShortVideosAllowed(boolean value) {
        this.shortVideosAllowed = value;
    }

    public boolean getStoriesAllowed() {
        return storiesAllowed;
    }

    public void setStoriesAllowed(boolean value) {
        this.storiesAllowed = value;
    }

    public boolean getStoriesCoverAllowed() {
        return storiesCoverAllowed;
    }

    public void setStoriesCoverAllowed(boolean value) {
        this.storiesCoverAllowed = value;
    }

    public Long getLyricsID() {
        return lyricsID;
    }

    public void setLyricsID(Long value) {
        this.lyricsID = value;
    }

    public Long getGenreID() {
        return genreID;
    }

    public void setGenreID(Long value) {
        this.genreID = value;
    }
}
