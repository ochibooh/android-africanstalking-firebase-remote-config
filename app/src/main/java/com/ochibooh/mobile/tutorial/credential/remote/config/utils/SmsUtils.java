package com.ochibooh.mobile.tutorial.credential.remote.config.utils;

import android.content.Context;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import com.ochibooh.mobile.tutorial.credential.remote.config.config.retrofit.RetrofitConfigurer;
import com.ochibooh.mobile.tutorial.credential.remote.config.integ.at.AfricasTalkingService;
import com.ochibooh.mobile.tutorial.credential.remote.config.model.SmsMessage;
import com.ochibooh.mobile.tutorial.credential.remote.config.work.SmsMessageSaveWorker;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
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

    public void save(@NonNull Context context, @NonNull SmsMessage smsMessage) {
        try {
            if (smsMessage.getRecipients() != null && smsMessage.getRecipients().size() > 0 && !SharedUtils.getInstance().isNullOrEmpty(smsMessage.getMessage())) {
                WorkRequest work = new OneTimeWorkRequest.Builder(SmsMessageSaveWorker.class)
                        .addTag(UUID.randomUUID().toString())
                        .setInputData(new Data.Builder().putStringArray("recipients", smsMessage.getRecipients().toArray(new String[0])).putString("message", smsMessage.getMessage()).putInt("status", smsMessage.getStatus()).putString("httpResponse", smsMessage.getHttpResponse()).build())
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

    public boolean send(@NonNull List<String> recipients, @NonNull String message) {
        AtomicBoolean res = new AtomicBoolean(false);
        try {
            String apiKey = "8309831a432e70459020e2bdda63a979fb43ac6930dfa848cadc99e0755a2854";
            String username = "credential-remote-config";
            String baseUrl = "";
            if (!SharedUtils.getInstance().isNullOrEmpty(username) && !SharedUtils.getInstance().isNullOrEmpty(apiKey)) {
                if (recipients.size() > 0 && !SharedUtils.getInstance().isNullOrEmpty(message)) {
                    AfricasTalkingService africasTalkingService = RetrofitConfigurer.getAfricasTalking(baseUrl).create(AfricasTalkingService.class);
                    africasTalkingService.sendSms(apiKey, username, TextUtils.join(",", recipients), message)
                            .enqueue(new Callback<SmsMessage>() {
                                @Override
                                public void onResponse(@NonNull Call<SmsMessage> call, @NonNull Response<SmsMessage> response) {
                                    log.log(Level.INFO, String.format("Send sms response [ %s ]", response));
                                    if (response.isSuccessful() && response.code() == 200) {

                                    } else {
                                        log.log(Level.WARNING, String.format("Send sms failed [ error=%s, body=%s ]", response.errorBody(), response.body()));
                                    }
                                }

                                @Override
                                public void onFailure(@NonNull Call<SmsMessage> call, @NonNull Throwable t) {
                                    log.log(Level.SEVERE, String.format("Send sms error [ %s ]", t.getMessage()), t);
                                }
                            });
                } else {
                    throw new Exception(String.format("Invalid sms message - recipients=%s, message=%s", recipients.size(), message.length()));
                }
            } else {
                throw new Exception(String.format("Invalid credentials - username=%s***, apiKey=%s", username.length() > 0 ? username.charAt(0) : "*", apiKey.length() > 0 ? apiKey.charAt(0) : "*"));
            }
        } catch (Exception e) {
            log.log(Level.SEVERE, String.format("Sms send error [ %s ]", e.getMessage()), e);
        }
        return res.get();
    }
}
