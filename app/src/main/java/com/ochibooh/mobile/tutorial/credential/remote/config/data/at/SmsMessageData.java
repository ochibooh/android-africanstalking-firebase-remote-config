package com.ochibooh.mobile.tutorial.credential.remote.config.data.at;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
public class SmsMessageData implements Serializable {
    @SerializedName(value = "Message")
    private String message;

    @SerializedName(value = "Recipients")
    private List<SmsMessageRecipientData> recipients;
}
