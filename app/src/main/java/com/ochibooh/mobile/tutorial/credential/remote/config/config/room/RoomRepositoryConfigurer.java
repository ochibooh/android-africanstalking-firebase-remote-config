package com.ochibooh.mobile.tutorial.credential.remote.config.config.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.ochibooh.mobile.tutorial.credential.remote.config.model.SmsMessage;
import com.ochibooh.mobile.tutorial.credential.remote.config.repo.SmsMessageRepository;

@Database(
        entities = {SmsMessage.class},
        version = 1
)
public abstract class RoomRepositoryConfigurer extends RoomDatabase {
    public abstract SmsMessageRepository smsMessageRepository();
}
