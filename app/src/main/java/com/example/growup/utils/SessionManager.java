package com.example.growup.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.example.growup.data.model.profile.Profile;

public class SessionManager {

    private static final String ACCESS_TOKEN_KEY = "ACCESS_TOKEN";
    private static final String REFRESH_TOKEN_KEY = "REFRESH_TOKEN";
    private static final String TOKEN_TIME_KEY = "token_time";
    private static final long TOKEN_VALIDITY = 15 * 60 * 1000;
    private final SharedPreferences prefs;
    private final SharedPreferences.Editor editor;
    private final Gson gson = new Gson();

    public SessionManager(Context context) {
        prefs = context.getSharedPreferences("APP_PREFS", Context.MODE_PRIVATE);
        editor = prefs.edit();
    }

    public void saveToken(String token) {
        long now = System.currentTimeMillis();

        editor.putString(ACCESS_TOKEN_KEY, token);
        editor.putLong(TOKEN_TIME_KEY, now);
        editor.commit();
    }

    public String getToken() {
        return prefs.getString(ACCESS_TOKEN_KEY, null);
    }

    public void saveRefreshToken(String token) {
        editor.putString(REFRESH_TOKEN_KEY, token);
        editor.commit();
    }

    public String getRefreshToken() {
        return prefs.getString(REFRESH_TOKEN_KEY, null);
    }

    public boolean isTokenValid() {
        long savedTime = prefs.getLong(TOKEN_TIME_KEY, 0);
        long now = System.currentTimeMillis();
        return (now - savedTime) < TOKEN_VALIDITY;
    }
    public boolean isLoggedIn() {
        return getToken() != null;
    }

    public void clear() {
        editor.remove(ACCESS_TOKEN_KEY);
        editor.remove(REFRESH_TOKEN_KEY);
        editor.remove("profile");
        editor.apply();
    }

    public void saveProfile(Profile profile) {
        prefs.edit().putString("profile", gson.toJson(profile)).apply();
    }
    public Profile getProfile() {
        String json = prefs.getString("profile", null);
        return json != null ? gson.fromJson(json, Profile.class) : null;
    }
}
