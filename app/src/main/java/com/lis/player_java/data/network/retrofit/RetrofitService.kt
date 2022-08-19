package com.lis.player_java.data.network.retrofit

import com.lis.player_java.data.model.VkMusic
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
    @GET("audio.get?v=5.95")
    suspend fun getMusic(
        @Query("access_token") accessToken: String?,
        @Query("count") count: Int,
        @Query("offset") offset: Int
    ): Response<VkMusic>

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