package com.ochibooh.mobile.tutorial.credential.remote.config.work;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Transaction;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.ochibooh.mobile.tutorial.credential.remote.config.config.room.RoomDatabaseConfigurer;
import com.ochibooh.mobile.tutorial.credential.remote.config.model.SmsMessage;
import com.ochibooh.mobile.tutorial.credential.remote.config.utils.SharedUtils;

import java.sql.Timestamp;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;

import lombok.extern.java.Log;

@Log
public class SmsMessageSaveWorker extends Worker {
    private Context context;

    public SmsMessageSaveWorker(@NonNull Context context, @NonNull WorkerParameters params) {
        super(context, params);
        this.context = context;
    }

    @Transaction
    @NonNull
    @Override
    public Result doWork() {
        AtomicBoolean res = new AtomicBoolean(false);
        try {
            Data data = getInputData();
            String recipient = data.getString("recipient");
            String message = data.getString("message");
            int status = data.getInt("status", 0);
            String httpResponse = data.getString("httpResponse");
            if (!SharedUtils.getInstance().isNullOrEmpty(recipient) && !SharedUtils.getInstance().isNullOrEmpty(message)) {
                SmsMessage smsMessage = new SmsMessage();
                smsMessage.setRecipient(Objects.requireNonNull(recipient).trim());
                smsMessage.setMessage(message);
                smsMessage.setStatus(status);
                smsMessage.setHttpResponse(httpResponse);
                smsMessage.setCreationDate(new Timestamp(System.currentTimeMillis()));
                RoomDatabaseConfigurer.getRepository(this.context).smsMessageRepository().insert(smsMessage);
                res.set(true);
            }
        } catch (Exception e) {
            log.log(Level.SEVERE, String.format("Sms message save worker error [ %s ]", e.getMessage()), e);
        }
        return res.get() ? Result.success() : Result.failure();
    }
}
