package com.ochibooh.mobile.tutorial.credential.remote.config.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.google.gson.annotations.SerializedName;
import com.ochibooh.mobile.tutorial.credential.remote.config.utils.constants.RoomTables;
import com.ochibooh.mobile.tutorial.credential.remote.config.utils.converter.StringListConverter;
import com.ochibooh.mobile.tutorial.credential.remote.config.utils.converter.TimestampConverter;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import lombok.Data;

@Data
@Entity(tableName = RoomTables.TABLE_SMS_MESSAGE, indices = { @Index(value = { "id" }, unique = true)})
public class SmsMessage implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id", typeAffinity = ColumnInfo.INTEGER)
    @SerializedName("id")
    private Integer id;

    @TypeConverters(value = StringListConverter.class)
    @ColumnInfo(name = "recipients", typeAffinity = ColumnInfo.TEXT)
    @SerializedName("recipients")
    private List<String> recipients;

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
