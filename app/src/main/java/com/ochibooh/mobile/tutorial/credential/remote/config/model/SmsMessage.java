package com.ochibooh.mobile.tutorial.credential.remote.config.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.google.gson.annotations.SerializedName;
import com.ochibooh.mobile.tutorial.credential.remote.config.utils.constants.RoomTables;
import com.ochibooh.mobile.tutorial.credential.remote.config.utils.converter.TimestampConverter;

import java.io.Serializable;
import java.sql.Timestamp;

import lombok.Data;

@Data
@Entity(tableName = RoomTables.TABLE_SMS_MESSAGE, indices = { @Index(value = { "id" }, unique = true)})
public class SmsMessage implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id", typeAffinity = ColumnInfo.INTEGER)
    @SerializedName("id")
    public Integer id;

    @ColumnInfo(name = "phone", typeAffinity = ColumnInfo.TEXT)
    @SerializedName("phone")
    public String phone;

    @ColumnInfo(name = "message", typeAffinity = ColumnInfo.TEXT)
    @SerializedName("message")
    public String message;

    @ColumnInfo(name = "sent", typeAffinity = ColumnInfo.INTEGER)
    @SerializedName("sent")
    public boolean sent;

    @ColumnInfo(name = "http_response", typeAffinity = ColumnInfo.TEXT)
    @SerializedName("httpResponse")
    public String httpResponse;

    @TypeConverters(value = TimestampConverter.class)
    @ColumnInfo(name = "creation_date", typeAffinity = ColumnInfo.TEXT)
    @SerializedName("creationDate")
    public Timestamp creationDate;
}
