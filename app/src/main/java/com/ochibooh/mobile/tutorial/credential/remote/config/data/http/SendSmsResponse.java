package com.ochibooh.mobile.tutorial.credential.remote.config.data.http;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
public class SendSmsResponse implements Serializable {
    private String test;
}
