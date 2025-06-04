package com.example.growup.data.api.auth;

import com.google.gson.annotations.SerializedName;

public class RegisterResponse {
    @SerializedName("message")
    private String message;

    public String getMessage() {
        return message;
    }
}

