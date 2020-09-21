package com.ochibooh.mobile.tutorial.credential.remote.config.view.pages;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.ochibooh.mobile.tutorial.credential.remote.config.R;

import lombok.extern.java.Log;

@Log
public class SmsMessagesPage extends Fragment {
    public SmsMessagesPage() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.page_sms_messages, container, false);
    }
}