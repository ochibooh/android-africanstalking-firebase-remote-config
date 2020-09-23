package com.ochibooh.mobile.tutorial.credential.remote.config.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.google.gson.annotations.SerializedName;
import com.ochibooh.mobile.tutorial.credential.remote.config.utils.Constants;
import com.ochibooh.mobile.tutorial.credential.remote.config.utils.converter.TimestampConverter;

import java.io.Serializable;
import java.sql.Timestamp;

import lombok.Data;

@Data
@Entity(tableName = Constants.TABLE_SMS_MESSAGE, indices = { @Index(value = { "id" }, unique = true)})
public class SmsMessage implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id", typeAffinity = ColumnInfo.INTEGER)
    @SerializedName("id")
    private Integer id;

    @ColumnInfo(name = "recipient", typeAffinity = ColumnInfo.TEXT)
    @SerializedName("recipient")
    private String recipient;

    @ColumnInfo(name = "message", typeAffinity = ColumnInfo.TEXT)
    @SerializedName("message")
    private String message;

    @ColumnInfo(name = "status", typeAffinity = ColumnInfo.INTEGER)
    @SerializedName("status")
    private int status;

    @ColumnInfo(name = "http_response", typeAffinity = ColumnInfo.TEXT)
    @SerializedName("httpResponse")
    private String httpResponse;

    @TypeConverters(value = TimestampConverter.class)
    @ColumnInfo(name = "creation_date", typeAffinity = ColumnInfo.TEXT)
    @SerializedName("creationDate")
    private Timestamp creationDate;
}
