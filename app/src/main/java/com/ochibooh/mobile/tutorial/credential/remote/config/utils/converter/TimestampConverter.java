package com.ochibooh.mobile.tutorial.credential.remote.config.utils.converter;

import androidx.room.TypeConverter;

import com.ochibooh.mobile.tutorial.credential.remote.config.utils.SharedUtils;

import java.sql.Timestamp;

public class TimestampConverter {
    @TypeConverter
    public String fromTimestamp (Timestamp timestamp){
        return timestamp != null ? timestamp.toString() : null;
    }

    @TypeConverter
    public Timestamp toTimestamp(String s){
        return !SharedUtils.getInstance().isNullOrEmpty(s) ? Timestamp.valueOf(s) : null;
    }
}
