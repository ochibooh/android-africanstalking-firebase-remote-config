package com.ochibooh.mobile.tutorial.credential.remote.config.utils;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.gson.Gson;
import com.ochibooh.mobile.tutorial.credential.remote.config.config.retrofit.RetrofitConfigurer;
import com.ochibooh.mobile.tutorial.credential.remote.config.config.room.RoomDatabaseConfigurer;
import com.ochibooh.mobile.tutorial.credential.remote.config.data.http.response.SendSmsResponse;
import com.ochibooh.mobile.tutorial.credential.remote.config.integ.at.AfricasTalkingService;
import com.ochibooh.mobile.tutorial.credential.remote.config.model.SmsMessage;
import com.ochibooh.mobile.tutorial.credential.remote.config.work.SmsMessageSaveWorker;

import java.util.List;
import java.util.UUID;
import java.util.logging.Level;

import lombok.extern.java.Log;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Log
public class SmsUtils {
    private static SmsUtils instance;

    public static SmsUtils getInstance() {
        if (instance == null) {
            instance = new SmsUtils();
        }
        return instance;
    }

    private void save(@NonNull Context context, @NonNull SmsMessage smsMessage) {
        try {
            if (!SharedUtils.getInstance().isNullOrEmpty(smsMessage.getRecipient()) && !SharedUtils.getInstance().isNullOrEmpty(smsMessage.getMessage())) {
                WorkRequest work = new OneTimeWorkRequest.Builder(SmsMessageSaveWorker.class)
                        .addTag(UUID.randomUUID().toString())
                        .setInputData(new Data.Builder().putString("recipient", smsMessage.getRecipient()).putString("message", smsMessage.getMessage()).putInt("status", smsMessage.getStatus()).putString("httpResponse", smsMessage.getHttpResponse()).build())
                        .setConstraints(new Constraints.Builder().setRequiresStorageNotLow(true).build())
                        .build();
                WorkManager.getInstance(context).enqueue(work);
            } else {
                throw new Exception(String.format("Invalid recipients or message - %s", smsMessage));
            }
        } catch (Exception e) {
            log.log(Level.SEVERE, String.format("Sms save error [ %s ]", e.getMessage()), e);
        }
    }

    public void send(@NonNull Context context, @NonNull String recipient, @NonNull String message) {
        SmsMessage smsMessage = new SmsMessage();
        try {
            smsMessage.setRecipient(recipient);
            smsMessage.setMessage(message);
            smsMessage.setStatus(0);

            FirebaseRemoteConfig firebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
            String apiKey = firebaseRemoteConfig.getString("africas_talking_api_key");
            String username = firebaseRemoteConfig.getString("africas_talking_api_username");
            String baseUrl = firebaseRemoteConfig.getString("africas_talking_base_url");
            if (!SharedUtils.getInstance().isNullOrEmpty(username) && !SharedUtils.getInstance().isNullOrEmpty(apiKey)) {
                if (!SharedUtils.getInstance().isNullOrEmpty(recipient) && !SharedUtils.getInstance().isNullOrEmpty(message)) {
                    AfricasTalkingService africasTalkingService = RetrofitConfigurer.getAfricasTalking(baseUrl).create(AfricasTalkingService.class);
                    africasTalkingService.sendSms(apiKey, username, recipient, message)
                            .enqueue(new Callback<SendSmsResponse>() {
                                @Override
                                public void onResponse(@NonNull Call<SendSmsResponse> call, @NonNull Response<SendSmsResponse> response) {
                                    if (response.isSuccessful() && response.body() != null) {
                                        smsMessage.setHttpResponse(new Gson().toJson(response.body()));
                                        smsMessage.setStatus(1);
                                        log.log(Level.INFO, String.format("Send sms response [ %s ]", response.body()));
                                    } else {
                                        log.log(Level.WARNING, String.format("Send sms failed [ %s ]", response.raw()));
                                    }
                                }

                                @Override
                                public void onFailure(@NonNull Call<SendSmsResponse> call, @NonNull Throwable t) {
                                    log.log(Level.SEVERE, String.format("Send sms request error [ %s ]", t.getMessage()), t);
                                }
                            });
                } else {
                    throw new Exception(String.format("Invalid sms message - recipient=%s, message=%s", recipient, message.length()));
                }
            } else {
                throw new Exception(String.format("Invalid credentials - username=%s***, apiKey=%s", username.length() > 0 ? username.charAt(0) : "*", apiKey.length() > 0 ? apiKey.charAt(0) : "*"));
            }
        } catch (Exception e) {
            log.log(Level.SEVERE, String.format("Sms send error [ %s ]", e.getMessage()), e);
        } finally {
            save(context, smsMessage);
        }
    }

    public LiveData<List<SmsMessage>> smsAll(@NonNull Context context) {
        return RoomDatabaseConfigurer.getRepository(context).smsMessageRepository().getAll();
    }

    public LiveData<List<SmsMessage>> smsAllByRecipient(@NonNull Context context, @NonNull String recipient) {
        return RoomDatabaseConfigurer.getRepository(context).smsMessageRepository().getAllByRecipient(recipient);
    }
}
