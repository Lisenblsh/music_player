package com.lis.player_java.data.Model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Item {
    private String artist;
    private long id;
    private long ownerID;
    private String title;
    private long duration;
    private String accessKey;
    private Ads ads;
    private boolean isExplicit;
    private boolean isLicensed;
    private String trackCode;
    private String url;
    private long date;
    private boolean isHq;
    private Album album;
    private Artist[] mainArtists;
    private Artist[] featuredArtists;
    private boolean shortVideosAllowed;
    private boolean storiesAllowed;
    private boolean storiesCoverAllowed;
    private Long lyricsID;
    private Long genreID;

    @JsonProperty("artist")
    public String getArtist() {
        return artist;
    }

    @JsonProperty("artist")
    public void setArtist(String value) {
        this.artist = value;
    }

    @JsonProperty("id")
    public long getID() {
        return id;
    }

    @JsonProperty("id")
    public void setID(long value) {
        this.id = value;
    }

    @JsonProperty("owner_id")
    public long getOwnerID() {
        return ownerID;
    }

    @JsonProperty("owner_id")
    public void setOwnerID(long value) {
        this.ownerID = value;
    }

    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    @JsonProperty("title")
    public void setTitle(String value) {
        this.title = value;
    }

    @JsonProperty("duration")
    public long getDuration() {
        return duration;
    }

    @JsonProperty("duration")
    public void setDuration(long value) {
        this.duration = value;
    }

    @JsonProperty("access_key")
    public String getAccessKey() {
        return accessKey;
    }

    @JsonProperty("access_key")
    public void setAccessKey(String value) {
        this.accessKey = value;
    }

    @JsonProperty("ads")
    public Ads getAds() {
        return ads;
    }

    @JsonProperty("ads")
    public void setAds(Ads value) {
        this.ads = value;
    }

    @JsonProperty("is_explicit")
    public boolean getIsExplicit() {
        return isExplicit;
    }

    @JsonProperty("is_explicit")
    public void setIsExplicit(boolean value) {
        this.isExplicit = value;
    }

    @JsonProperty("is_licensed")
    public boolean getIsLicensed() {
        return isLicensed;
    }

    @JsonProperty("is_licensed")
    public void setIsLicensed(boolean value) {
        this.isLicensed = value;
    }

    @JsonProperty("track_code")
    public String getTrackCode() {
        return trackCode;
    }

    @JsonProperty("track_code")
    public void setTrackCode(String value) {
        this.trackCode = value;
    }

    @JsonProperty("url")
    public String getURL() {
        return url;
    }

    @JsonProperty("url")
    public void setURL(String value) {
        this.url = value;
    }

    @JsonProperty("date")
    public long getDate() {
        return date;
    }

    @JsonProperty("date")
    public void setDate(long value) {
        this.date = value;
    }

    @JsonProperty("is_hq")
    public boolean getIsHq() {
        return isHq;
    }

    @JsonProperty("is_hq")
    public void setIsHq(boolean value) {
        this.isHq = value;
    }

    @JsonProperty("album")
    public Album getAlbum() {
        return album;
    }

    @JsonProperty("album")
    public void setAlbum(Album value) {
        this.album = value;
    }

    @JsonProperty("main_artists")
    public Artist[] getMainArtists() {
        return mainArtists;
    }

    @JsonProperty("main_artists")
    public void setMainArtists(Artist[] value) {
        this.mainArtists = value;
    }

    @JsonProperty("featured_artists")
    public Artist[] getFeaturedArtists() {
        return featuredArtists;
    }

    @JsonProperty("featured_artists")
    public void setFeaturedArtists(Artist[] value) {
        this.featuredArtists = value;
    }

    @JsonProperty("short_videos_allowed")
    public boolean getShortVideosAllowed() {
        return shortVideosAllowed;
    }

    @JsonProperty("short_videos_allowed")
    public void setShortVideosAllowed(boolean value) {
        this.shortVideosAllowed = value;
    }

    @JsonProperty("stories_allowed")
    public boolean getStoriesAllowed() {
        return storiesAllowed;
    }

    @JsonProperty("stories_allowed")
    public void setStoriesAllowed(boolean value) {
        this.storiesAllowed = value;
    }

    @JsonProperty("stories_cover_allowed")
    public boolean getStoriesCoverAllowed() {
        return storiesCoverAllowed;
    }

    @JsonProperty("stories_cover_allowed")
    public void setStoriesCoverAllowed(boolean value) {
        this.storiesCoverAllowed = value;
    }

    @JsonProperty("lyrics_id")
    public Long getLyricsID() {
        return lyricsID;
    }

    @JsonProperty("lyrics_id")
    public void setLyricsID(Long value) {
        this.lyricsID = value;
    }

    @JsonProperty("genre_id")
    public Long getGenreID() {
        return genreID;
    }

    @JsonProperty("genre_id")
    public void setGenreID(Long value) {
        this.genreID = value;
    }
}
