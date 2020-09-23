package com.ochibooh.mobile.tutorial.credential.remote.config.utils;

import android.content.Context;

import androidx.annotation.NonNull;

import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;

import io.michaelrocks.libphonenumber.android.PhoneNumberUtil;
import io.michaelrocks.libphonenumber.android.Phonenumber;
import lombok.extern.java.Log;

@Log
public class PhoneUtils {
    private static PhoneUtils instance;

    public static PhoneUtils getInstance() {
        if (instance == null) {
            instance = new PhoneUtils();
        }
        return instance;
    }

    public Phonenumber.PhoneNumber phone(@NonNull Context context, @NonNull Integer countryCode, @NonNull String phoneNumber){
        AtomicReference<Phonenumber.PhoneNumber> res = new AtomicReference<>(null);
        try {
            Phonenumber.PhoneNumber p = new Phonenumber.PhoneNumber().setCountryCode(countryCode).setNationalNumber(Long.parseLong(phoneNumber));
            if (PhoneNumberUtil.createInstance(context).isValidNumber(p)){
                res.set(p);
            }
        } catch (Exception e){
            log.log(Level.SEVERE, String.format("Phone number generator error [ %s ]", e.getMessage()), e);
        }
        return res.get();
    }

    public Phonenumber.PhoneNumber phone(@NonNull Context context, @NonNull String countryIso, @NonNull String phoneNumber){
        AtomicReference<Phonenumber.PhoneNumber> res = new AtomicReference<>(null);
        try {
            PhoneNumberUtil util = PhoneNumberUtil.createInstance(context);
            Phonenumber.PhoneNumber p = util.parse(phoneNumber, countryIso);
            if (util.isValidNumber(p)){
                res.set(p);
            }
        } catch (Exception e){
            log.log(Level.SEVERE, String.format("Phone number generator error [ %s ]", e.getMessage()), e);
        }
        return res.get();
    }

    public String format(@NonNull Context context, @NonNull Phonenumber.PhoneNumber phoneNumber, @NonNull PhoneNumberUtil.PhoneNumberFormat format){
        AtomicReference<String> res = new AtomicReference<>(null);
        try {
            PhoneNumberUtil util = PhoneNumberUtil.createInstance(context);
            if (util.isValidNumber(phoneNumber)){
                res.set(util.format(phoneNumber, format));
            }
        } catch (Exception e){
            log.log(Level.SEVERE, String.format("Phone number format error [ %s ]", e.getMessage()), e);
        }
        return res.get();
    }
}
