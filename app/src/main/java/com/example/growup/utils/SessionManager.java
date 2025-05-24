package com.example.growup.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {

    private static final String ACCESS_TOKEN_KEY = "ACCESS_TOKEN";
    private static final String REFRESH_TOKEN_KEY = "REFRESH_TOKEN";
    private final SharedPreferences prefs;
    private final SharedPreferences.Editor editor;

    public SessionManager(Context context) {
        prefs = context.getSharedPreferences("APP_PREFS", Context.MODE_PRIVATE);
        editor = prefs.edit();
    }

    public void saveToken(String token) {
        editor.putString(ACCESS_TOKEN_KEY, token);
        editor.apply();
    }

    public String getToken() {
        return prefs.getString(ACCESS_TOKEN_KEY, null);
    }

    public void saveRefreshToken(String token) {
        editor.putString(REFRESH_TOKEN_KEY, token);
        editor.apply();
    }

    public String getRefreshToken() {
        return prefs.getString(REFRESH_TOKEN_KEY, null);
    }

    public boolean isLoggedIn() {
        return getToken() != null;
    }

    public void clear() {
        editor.clear().apply();
    }
}
