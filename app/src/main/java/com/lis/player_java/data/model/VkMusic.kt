package com.lis.player_java.data.model

import com.google.gson.annotations.SerializedName
import com.lis.player_java.data.model.Ads
import com.lis.player_java.data.model.Album
import com.lis.player_java.data.model.Thumb

data class VkMusic (
    @SerializedName("response")
    val response: Response
)

data class Response (
    @SerializedName("count")
    val count: Long,
    @SerializedName("items")
    val items: List<Item>
)

data class Item(
    @SerializedName("artist")
    val artist: String,
    @SerializedName("id")
    val id: Long,
    @SerializedName("owner_id")
    val ownerId: Long,
    @SerializedName("title")
    val title: String,
    @SerializedName("duration")
    val duration: Long = 0,
    @SerializedName("access_key")
    val accessKey: String,
    @SerializedName("ads")
    val ads: Ads?,
    @SerializedName("is_explicit")
    val isExplicit: Boolean = false,
    @SerializedName("is_licensed")
    val isLicensed: Boolean = false,
    @SerializedName("track_code")
    val trackCode: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("date")
    val date: Long,
    @SerializedName("is_hq")
    val isHq: Boolean = false,
    @SerializedName("album")
    val album: Album?,
    @SerializedName("main_artists")
    val mainArtists: List<Artist>,
    @SerializedName("featured_artists")
    val featuredArtists: List<Artist>,
    @SerializedName("short_videos_allowed")
    val shortVideosAllowed: Boolean = false,
    @SerializedName("stories_allowed")
    val storiesAllowed: Boolean = false,
    @SerializedName("stories_cover_allowed")
    val storiesCoverAllowed: Boolean = false,
    @SerializedName("lyrics_id")
    val lyricsId: Long?,
    @SerializedName("genre_id")
    val genreId: Long?,
)

data class Ads(
    @SerializedName("content_id")
    val contentId: String,
    @SerializedName("duration")
    val duration: String,
    @SerializedName("account_age_type")
    val accountAgeType: String,
    @SerializedName("puid1")
    val puid1: String,
    @SerializedName("puid22")
    val puid22: String
)

data class Album(
    @SerializedName("id")
    val id: Long,
    @SerializedName("title")
    val title: String,
    @SerializedName("owner_id")
    val ownerId: Long,
    @SerializedName("access_key")
    val accessKey: String,
    @SerializedName("thumb")
    val thumb: Thumb?
)

data class Thumb (
    @SerializedName("width")
    val width: Long,
    @SerializedName("height")
    val height: Long,
    @SerializedName("photo_34")
    val photo34: String,
    @SerializedName("photo_68")
    val photo68: String,
    @SerializedName("photo_135")
    val photo135: String,
    @SerializedName("photo_270")
    val photo270: String,
    @SerializedName("photo_300")
    val photo300: String,
    @SerializedName("photo_600")
    val photo600: String,
    @SerializedName("photo_1200")
    val photo1200: String,
)

data class Artist(
    @SerializedName("name")
    val name: String,
    @SerializedName("domain")
    val domain: String,
    @SerializedName("id")
    val id: String
)