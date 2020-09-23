package com.ochibooh.mobile.tutorial.credential.remote.config.integ.at;

import com.ochibooh.mobile.tutorial.credential.remote.config.data.http.response.SendSmsResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface AfricasTalkingService {
    @Headers(value = {"Accept: application/json"})
    @POST(value = "messaging")
    @FormUrlEncoded
    Call<SendSmsResponse> sendSms(@Header("apiKey") String apiKey, @Field("username") String username, @Field("to") String recipient, @Field("message") String message);
}
