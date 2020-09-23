package com.ochibooh.mobile.tutorial.credential.remote.config.work;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.ochibooh.mobile.tutorial.credential.remote.config.utils.SharedUtils;
import com.ochibooh.mobile.tutorial.credential.remote.config.utils.SmsUtils;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;

import lombok.extern.java.Log;

@Log
public class SmsMessageSendWork extends Worker {
    private Context context;

    public SmsMessageSendWork(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.context = context;
    }

    @NonNull
    @Override
    public Result doWork() {
        AtomicBoolean res = new AtomicBoolean(false);
        try {
            Data data = getInputData();
            String recipient = data.getString("recipient");
            String message = data.getString("message");
            if (!SharedUtils.getInstance().isNullOrEmpty(recipient) && !SharedUtils.getInstance().isNullOrEmpty(message)) {
                SmsUtils.getInstance().send(this.context, Objects.requireNonNull(recipient), Objects.requireNonNull(message));
                res.set(true);
            } else {
                log.log(Level.WARNING, String.format("Sms message send invalid data [ recipient=%s, message=%s ]", recipient, message));
            }
        } catch (Exception e) {
            log.log(Level.SEVERE, String.format("Sms message send worker error [ %s ]", e.getMessage()), e);
        }
        return res.get() ? Result.success() : Result.failure();
    }
}
