package com.ochibooh.mobile.tutorial.credential.remote.config.utils;

import android.text.format.DateFormat;

import java.util.Calendar;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;

import lombok.extern.java.Log;

@Log
public class SharedUtils {
    private static SharedUtils instance;

    public static SharedUtils getInstance() {
        if (instance == null) {
            instance = new SharedUtils();
        }
        return instance;
    }

    public boolean isNullOrEmpty(String s) {
        return s == null || s.isEmpty() || s.trim().isEmpty();
    }

    public String date(long time) {
        AtomicReference<String> res = new AtomicReference<>("");
        try {
            Calendar smsTime = Calendar.getInstance();
            smsTime.setTimeInMillis(time);
            Calendar now = Calendar.getInstance();
            final String timeFormatString = "h:mm aa";
            final String dateTimeFormatString = "EEEE, MMMM d, h:mm aa";
            if (now.get(Calendar.DATE) == smsTime.get(Calendar.DATE) ) {
                res.set(String.format("Today %s", DateFormat.format(timeFormatString, smsTime)));
            } else if (now.get(Calendar.DATE) - smsTime.get(Calendar.DATE) == 1  ){
                res.set(String.format("Yesterday %s", DateFormat.format(timeFormatString, smsTime)));
            } else if (now.get(Calendar.YEAR) == smsTime.get(Calendar.YEAR)) {
                res.set(DateFormat.format(dateTimeFormatString, smsTime).toString());
            } else {
                res.set(DateFormat.format("MMMM dd yyyy, h:mm aa", smsTime).toString());
            }
        } catch (Exception e) {
            log.log(Level.SEVERE, String.format("Format date error [ %s ]", e.getMessage()), e);
        }
        return res.get();
    }
}
