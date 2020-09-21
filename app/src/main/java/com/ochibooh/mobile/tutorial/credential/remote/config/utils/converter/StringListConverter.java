package com.ochibooh.mobile.tutorial.credential.remote.config.utils.converter;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ochibooh.mobile.tutorial.credential.remote.config.utils.SharedUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import lombok.extern.java.Log;

@Log
public class StringListConverter {
    private Gson gson = new Gson();

    @TypeConverter
    public String fromStringList(List<String> s) {
        return s != null && s.size() > 0 ? gson.toJson(s) : null;
    }

    @TypeConverter
    public List<String> toStringList(String s) {
        List<String> list = new ArrayList<>();
        try {
            if (!SharedUtils.getInstance().isNullOrEmpty(s)) {
                list = gson.fromJson(s, new TypeToken<List<String>>(){}.getType());
            }
        } catch (Exception e) {
            log.log(Level.SEVERE, String.format("To string list error [ %s ]", e.getMessage()), e);
        }
        return list;
    }
}
