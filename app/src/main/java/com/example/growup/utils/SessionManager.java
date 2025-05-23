package com.example.growup.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    private final SharedPreferences prefs;
    private final SharedPreferences.Editor editor;

    public SessionManager(Context context) {
        prefs = context.getSharedPreferences("APP_PREFS", Context.MODE_PRIVATE);
        editor = prefs.edit();
    }

    public void saveToken(String token) {
        editor.putString("JWT_TOKEN", token);
        editor.apply();
    }

    public String getToken() {
        return prefs.getString("JWT_TOKEN", null);
    }

    public void clear() {
        editor.clear().apply();
    }
}
