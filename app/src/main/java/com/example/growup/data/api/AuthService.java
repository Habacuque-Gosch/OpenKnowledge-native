package com.example.growup.data.api;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthService {

    @POST("login/")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

    @POST("/register/")
    Call<LoginResponse> register(@Body RegisterRequest request);

}
