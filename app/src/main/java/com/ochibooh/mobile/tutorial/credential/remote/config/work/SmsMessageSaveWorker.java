package com.ochibooh.mobile.tutorial.credential.remote.config.work;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.ochibooh.mobile.tutorial.credential.remote.config.config.room.RoomDatabaseConfigurer;
import com.ochibooh.mobile.tutorial.credential.remote.config.model.SmsMessage;
import com.ochibooh.mobile.tutorial.credential.remote.config.repo.SmsMessageRepository;
import com.ochibooh.mobile.tutorial.credential.remote.config.utils.SharedUtils;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;

import lombok.extern.java.Log;

@Log
public class SmsMessageSaveWorker extends Worker {
    private SmsMessageRepository smsMessageRepository;

    public SmsMessageSaveWorker(@NonNull Context context, @NonNull WorkerParameters params) {
        super(context, params);

        this.smsMessageRepository = RoomDatabaseConfigurer.getRepository(context).smsMessageRepository();
    }

    @NonNull
    @Override
    public Result doWork() {
        AtomicBoolean res = new AtomicBoolean(false);
        try {
            Data data = getInputData();
            String[] recipients = data.getStringArray("recipients");
            String message = data.getString("message");
            int status = data.getInt("status", 0);
            String httpResponse = data.getString("httpResponse");
            if (recipients != null && !SharedUtils.getInstance().isNullOrEmpty(message)) {
                SmsMessage smsMessage = new SmsMessage();
                smsMessage.setRecipients(Arrays.asList(recipients));
                smsMessage.setMessage(message);
                smsMessage.setStatus(status);
                smsMessage.setHttpResponse(httpResponse);
                smsMessage.setCreationDate(Timestamp.valueOf(String.valueOf(System.currentTimeMillis())));
                smsMessageRepository.insert(smsMessage);
            }
        } catch (Exception e) {
            log.log(Level.SEVERE, String.format("Sms message save worker error [ %s ]", e.getMessage()), e);
        }
        return res.get() ? Result.success() : Result.failure();
    }
}
