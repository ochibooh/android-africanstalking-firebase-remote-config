package com.ochibooh.mobile.tutorial.credential.remote.config.repo;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.ochibooh.mobile.tutorial.credential.remote.config.model.SmsMessage;
import com.ochibooh.mobile.tutorial.credential.remote.config.utils.constants.RoomTables;

import java.util.List;

@Dao
public interface SmsMessageRepository {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(SmsMessage message);

    @Query(value = "SELECT * FROM " + RoomTables.TABLE_SMS_MESSAGE + " ORDER BY id DESC")
    LiveData<List<SmsMessage>> getAllOrderByIdDesc();
}
