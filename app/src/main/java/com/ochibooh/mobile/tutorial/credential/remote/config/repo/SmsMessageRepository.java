package com.ochibooh.mobile.tutorial.credential.remote.config.repo;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.ochibooh.mobile.tutorial.credential.remote.config.model.SmsMessage;
import com.ochibooh.mobile.tutorial.credential.remote.config.utils.Constants;

import java.util.List;

@Dao
public interface SmsMessageRepository {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(SmsMessage message);

    @Query(value = "SELECT * FROM " + Constants.TABLE_SMS_MESSAGE)
    LiveData<List<SmsMessage>> getAll();

    @Query(value = "SELECT * FROM " + Constants.TABLE_SMS_MESSAGE + " WHERE recipient = :recipient")
    LiveData<List<SmsMessage>> getAllByRecipient(String recipient);
}
