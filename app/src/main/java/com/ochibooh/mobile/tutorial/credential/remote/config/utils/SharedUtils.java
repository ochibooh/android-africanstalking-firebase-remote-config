package com.ochibooh.mobile.tutorial.credential.remote.config.utils;

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
        return s == null || s.isEmpty();
    }
}
