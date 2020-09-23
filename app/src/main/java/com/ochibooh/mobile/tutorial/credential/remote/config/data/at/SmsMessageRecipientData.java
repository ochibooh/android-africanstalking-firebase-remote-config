package com.ochibooh.mobile.tutorial.credential.remote.config.data.at;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
public class SmsMessageRecipientData implements Serializable {
    @SerializedName(value = "statusCode")
    private int code;

    @SerializedName(value = "number")
    private String number;

    @SerializedName(value = "status")
    private String status;

    @SerializedName(value = "cost")
    private String cost;

    @SerializedName(value = "messageId")
    private String messageId;
}
