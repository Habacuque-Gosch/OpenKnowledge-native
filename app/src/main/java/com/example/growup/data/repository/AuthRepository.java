package com.example.growup.data.repository;

import android.content.Context;

import com.example.growup.data.api.ApiClient;
import com.example.growup.data.api.AuthService;
import com.example.growup.data.api.LoginRequest;
import com.example.growup.data.api.LoginResponse;

import retrofit2.Call;

public class AuthRepository {
    private final AuthService authService;

    public AuthRepository(Context context) {
        authService = ApiClient.getClient(context).create(AuthService.class);
    }

    public Call<LoginResponse> login(String email, String password) {
        LoginRequest loginRequest = new LoginRequest(email, password);
        return authService.login(loginRequest);
    }
}
