package com.lis.player_java.data.network.retrofit

import com.lis.player_java.data.model.VkMusic
import com.lis.player_java.data.network.Filters
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {
    //audio.add
    @GET("audio.add?v=5.95")
    suspend fun addAudio(
        @Query("access_token") accessToken: String,
        @Query("owner_id") ownerId: Long,
        @Query("audio_id") audioId: Long,
    ): Response<String>

    //audio.addToPlaylist
    @GET("audio.addToPlaylist?v=5.95")
    suspend fun addToPlaylistAudio(
        @Query("access_token") accessToken: String,
        @Query("owner_id") ownerId: Long,
        @Query("playlist_id") playlistId: Long,
        @Query("audio_ids") audioIds: Long,
    ): Response<String>

    //audio.createPlaylist
    @GET("audio.createPlaylist?v=5.95")
    suspend fun createPlaylistAudio(
        @Query("access_token") accessToken: String,
        @Query("owner_id") ownerId: Long,
        @Query("title") title: String,
    ): Response<String>

    //audio.delete
    @GET("audio.delete?v=5.95")
    suspend fun deleteAudio(
        @Query("access_token") accessToken: String,
        @Query("owner_id") ownerId: Long,
        @Query("audio_id") audioId: Long,
    ): Response<String>

    //audio.deletePlaylist
    @GET("audio.deletePlaylist?v=5.95")
    suspend fun deletePlaylistAudio(
        @Query("access_token") accessToken: String,
        @Query("owner_id") ownerId: Long,
        @Query("playlist_id") playlistId: Long,
    ): Response<String>

    //audio.edit
    @GET("audio.edit?v=5.95")
    suspend fun editAudio(
        @Query("access_token") accessToken: String,
        @Query("owner_id") ownerId: Long,
        @Query("audio_id") audioId: Long,
        @Query("artist") artist: String?,
        @Query("title") title: String?,
        @Query("text") text: String?,
        @Query("genre_id") genreId: Int?,
        @Query("no_search") noSearch: Int?,
    ): Response<String>

    //audio.followPlaylist
    @GET("audio.followPlaylist?v=5.95")
    suspend fun followPlaylistAudio(
        @Query("access_token") accessToken: String,
        @Query("owner_id") ownerId: Long,
        @Query("playlist_id") playlistId: Long,
    ): Response<String>

    //audio.get
    @GET("audio.get?v=5.95")
    suspend fun getAudio(
        @Query("access_token") accessToken: String,
        @Query("count") count: Int?,
        @Query("offset") offset: Int?,
        @Query("owner_id") ownerId: Long?,
        @Query("album_id") albumId: Long?,
        @Query("access_key") accessKey: String,
    ): Response<VkMusic>

    //audio.getAlbumsByArtist
    @GET("audio.getAlbumsByArtist?v=5.95")
    suspend fun getAlbumsByArtistAudio(
        @Query("access_token") accessToken: String,
        @Query("artist_id") artistId: Long,
        @Query("count") count: Int?,
        @Query("offset") offset: Int?,
    ): Response<String>

    //audio.getArtistById
    @GET("audio.getArtistById?v=5.95")
    suspend fun getArtistByIdAudio(
        @Query("access_token") accessToken: String,
        @Query("artist_id") artistId: Long,
        @Query("extended") extended: String?,
    ): Response<String>

    //audio.getAudiosByArtist
    @GET("audio.getAudiosByArtist?v=5.95")
    suspend fun getAudiosByArtistAudio(
        @Query("access_token") accessToken: String,
        @Query("artist_id") artistId: Long,
        @Query("count") count: Int?,
        @Query("offset") offset: Int?,
    ): Response<String>

    //audio.getById
    @GET("audio.getById?v=5.95")
    suspend fun getByIdAudio(
        @Query("access_token") accessToken: String,
        @Query("audios") audios: String,
    ): Response<String>

    //audio.getCount
    @GET("audio.getCount?v=5.95")
    suspend fun getCountAudio(
        @Query("access_token") accessToken: String,
        @Query("owner_id") ownerId: Long,
    ): Response<String>

    //audio.getLyrics
    @GET("audio.getLyrics?v=5.95")
    suspend fun getLyricsAudio(
        @Query("access_token") accessToken: String,
        @Query("lyrics_id") lyricsId: Long,
    ): Response<String>

    //audio.getPlaylistById
    @GET("audio.getPlaylistById?v=5.95")
    suspend fun getPlaylistByIdAudio(
        @Query("access_token") accessToken: String,
        @Query("owner_id") ownerId: Long?,
        @Query("playlist_id") playlistId: Long,
        @Query("access_key") accessKey: String,
        @Query("count") count: Int?,
    ): Response<String>

    //audio.getPlaylists
    @GET("audio.getPlaylists?v=5.95")
    suspend fun getPlaylistsAudio(
        @Query("access_token") accessToken: String,
        @Query("owner_id") ownerId: Long?,
        @Query("count") count: Int?,
        @Query("offset") offset: Int?,
        @Query("extended") extended: Int?,
        @Query("fields") fields: String?,
        @Query("filters") filters: Filters?
    ): Response<String>

    //audio.getPopular
    @GET("audio.getPopular?v=5.95")
    suspend fun getPopularAudio(
        @Query("access_token") accessToken: String,
        @Query("offset") offset: Int?,
        @Query("count") count: Int?,
    ): Response<String>

    //audio.getRecommendations
    @GET("audio.getRecommendations?v=5.95")
    suspend fun getRecommendationsAudio(
        @Query("access_token") accessToken: String,
        @Query("user_id") userId: Long?,
        @Query("target_audio") targetAudio: String?,
        @Query("count") count: Int?,
        @Query("offset") offset: Int?,
    ): Response<String>

    //audio.removeFromPlaylist
    @GET("audio.removeFromPlaylist?v=5.95")
    suspend fun removeFromPlaylistAudio(
        @Query("access_token") accessToken: String,
        @Query("owner_id") ownerId: Long?,
        @Query("playlist_id") playlistId: Long,
        @Query("audio_ids") audioIds: String,
    ): Response<String>

    //audio.reorder
    //after or before need
    @GET("audio.reorder?v=5.95")
    suspend fun reorderAudio(
        @Query("access_token") accessToken: String,
        @Query("owner_id") ownerId: Long?,
        @Query("audio_id") audioId: Long,
        @Query("after") after: Long?,
        @Query("before") before: Long?,
    ): Response<String>

    //audio.search
    @GET("audio.search?v=5.95")
    suspend fun searchAudio(
        @Query("access_token") accessToken: String,
        @Query("q") q: String,
        @Query("count") count: Int?,
        @Query("offset") offset: Int?,
        @Query("auto_complete") autoComplete: Int?,
        @Query("performer_only") performerOnly: Int?,
    ): Response<String>

    //audio.searchAlbums
    @GET("audio.searchAlbums?v=5.95")
    suspend fun searchAlbumsAudio(
        @Query("access_token") accessToken: String,
        @Query("q") q: String,
        @Query("offset") offset: Int?,
        @Query("count") count: Int?,
    ): Response<String>

    //audio.searchArtists
    @GET("audio.searchArtists?v=5.95")
    suspend fun searchArtistsAudio(
        @Query("access_token") accessToken: String,
        @Query("q") q: String,
        @Query("count") count: Int?,
        @Query("offset") offset: Int?,
    ): Response<String>

    //audio.searchPlaylists
    @GET("audio.searchPlaylists?v=5.95")
    suspend fun searchPlaylistsAudio(
        @Query("access_token") accessToken: String,
        @Query("q") q: String,
        @Query("count") count: Int?,
        @Query("offset") offset: Int?,
        @Query("filters") filters: String?,
    ): Response<String>

    companion object {
        private const val BASE_URL = "https://api.vk.com/method/"

        fun create(userAgent: String): RetrofitService? {
            val okHttpClient = OkHttpClient().newBuilder()
                .addInterceptor(Interceptor { chain: Interceptor.Chain ->
                    val origin = chain.request()

                    val requestBuilder = origin.newBuilder()
                        .removeHeader("User-Agent")
                        .addHeader("User-Agent", userAgent)
                        .method(origin.method, origin.body)

                    val request: Request = requestBuilder.build()

                    chain.proceed(request)
                })
                .retryOnConnectionFailure(true)
                .build()
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(RetrofitService::class.java)
        }
    }
}