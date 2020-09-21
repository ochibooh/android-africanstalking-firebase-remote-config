package com.ochibooh.mobile.tutorial.credential.remote.config.config.retrofit;

import androidx.annotation.NonNull;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import lombok.extern.java.Log;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Log
public class RetrofitConfigurer {
    private static Retrofit africasTalking;

    private static OkHttpClient httpClient() {
        return new OkHttpClient.Builder()
                .followRedirects(true)
                .followSslRedirects(true)
                .readTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();
    }

    public static Retrofit getAfricasTalking(@NonNull String baseUrl) {
        if (africasTalking == null) {
            africasTalking = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(httpClient())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        if (!africasTalking.baseUrl().toString().equals(baseUrl)) {
            log.log(Level.INFO, String.format("Updating africa's talking base url [ previous=%s, new=%s ]", africasTalking.baseUrl().toString(), baseUrl));
            africasTalking = africasTalking.newBuilder().baseUrl(baseUrl).build();
        }
        return africasTalking;
    }
}
