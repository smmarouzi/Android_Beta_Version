package com.android.lucid.model;

import android.text.TextUtils;

import com.google.firebase.database.Exclude;
import com.google.gson.Gson;

public class UserExperienceModel {

    public static final String MODE_ENERGIZE = "energize";
    public static final String MODE_CALM = "calm";

    public String mode;
    public String trackName;
    public int beginFeelingValue;
    public int resultFeelingValue;
    public long timestamp;

    public UserExperienceModel() {
        mode = MODE_ENERGIZE;
        trackName = "";
        beginFeelingValue = -1;
        resultFeelingValue = -1;
        timestamp = System.currentTimeMillis();
    }

    @Exclude
    public boolean isEnergize() {
        return MODE_ENERGIZE.equals(mode);
    }

    @Exclude
    public String toJson() {
        return new Gson().toJson(this);
    }

    @Exclude
    public static UserExperienceModel getPack(String json) {
        if (TextUtils.isEmpty(json)) return null;
        return new Gson().fromJson(json, UserExperienceModel.class);
    }

}
