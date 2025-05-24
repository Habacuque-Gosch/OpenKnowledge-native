package com.example.growup.data.api;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface AuthService {

    @POST("login/")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

    @POST("register/")
    Call<RegisterResponse> register(@Body RegisterRequest request);

    @POST("auth/token/refresh/")
    Call<TokenResponse> refreshtoken(@Body RefreshRequest request);

}
