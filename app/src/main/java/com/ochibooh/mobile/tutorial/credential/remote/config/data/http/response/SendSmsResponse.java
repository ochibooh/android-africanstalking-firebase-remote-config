package com.ochibooh.mobile.tutorial.credential.remote.config.data.http.response;

import com.google.gson.annotations.SerializedName;
import com.ochibooh.mobile.tutorial.credential.remote.config.data.at.SmsMessageData;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
public class SendSmsResponse implements Serializable {
    @SerializedName(value = "SMSMessageData")
    private SmsMessageData messageData;
}
