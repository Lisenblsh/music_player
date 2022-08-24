package com.lis.player_java.vkaudiotoken.network

import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {
    @GET("token?grant_type=password&v=5.116&2fa_supported=1&scope=all")
    suspend fun getToken(
        @Query("lang") lang: String,
        @Query("client_id") clientId: String,
        @Query("client_secret") clientSecret: String,
        @Query("username") username: String,
        @Query("password") password: String,
        @Query("device_id") deviceID: String,
        @Query("code") code: String? = null,
        @Query("captcha_sid") captchaSid: String? = null,
        @Query("captcha_key") captchaKey: String? = null
    ): Response<String>

    @GET("auth.validatePhone?v=5.95")
    suspend fun validatePhone(
        @Query("sid") sid: String
    ): Response<String>

    companion object {
        fun create(userAgent: String, baseUrl: String): RetrofitService {

            val okHttpClient = OkHttpClient().newBuilder()
                .addInterceptor { chain ->
                    val origin = chain.request()

                    val requestBuilder = origin.newBuilder()
                        .removeHeader("User-Agent")
                        .addHeader("User-Agent", userAgent)
                        .method(origin.method, origin.body)

                    val request = requestBuilder.build()

                    chain.proceed(request)
                }
                .retryOnConnectionFailure(true)
                .build()
            return Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build()
                .create(RetrofitService::class.java)
        }
    }
}
