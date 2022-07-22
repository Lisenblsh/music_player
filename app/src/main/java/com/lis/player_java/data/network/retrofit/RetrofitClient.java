package com.lis.player_java.data.network.retrofit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static final String BASE_URL = "https://api.vk.com/method/";

    public static RetrofitService create(String userAgent) {
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .addInterceptor(chain -> {
                    Request origin = chain.request();

                    Request.Builder requestBuilder = origin.newBuilder()
                            .removeHeader("User-Agent")
                            .addHeader("User-Agent", userAgent)
                            .method(origin.method(), origin.body());

                    Request request = requestBuilder.build();

                    return chain.proceed(request);
                })
                .retryOnConnectionFailure(true)
                .build();

        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(RetrofitService.class);

    }
}
