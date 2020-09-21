package com.ochibooh.mobile.tutorial.credential.remote.config.integ.at;

import com.ochibooh.mobile.tutorial.credential.remote.config.data.http.SendSmsResponse;

import retrofit2.Call;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface AfricasTalkingService {
    @GET(value = "/messaging")
    @FormUrlEncoded
    Call<SendSmsResponse> sendSms(@Header("apiKey") String apiKey, @Path("username") String username, @Path("to") String recipients, @Path("message") String message);
}
