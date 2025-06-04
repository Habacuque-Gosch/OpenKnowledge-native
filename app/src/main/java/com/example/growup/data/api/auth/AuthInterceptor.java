package com.example.growup.data.api.auth;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.growup.data.api.auth.TokenResponse;
import com.example.growup.data.repository.AuthRepository;
import com.example.growup.utils.SessionManager;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AuthInterceptor implements Interceptor {
    private final SessionManager sessionManager;
    private final Object lock = new Object();

    public AuthInterceptor(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request originalRequest = chain.request();
        String url = originalRequest.url().toString();

        if (url.contains("/login") || url.contains("/register") || url.contains("/refresh")) {
            return chain.proceed(originalRequest);
        }

        if (!sessionManager.isTokenValid()) {
            synchronized (lock) {
                if (!sessionManager.isTokenValid()) {
                    String refreshToken = sessionManager.getRefreshToken();
                    if (refreshToken != null && !refreshToken.isEmpty()) {
                        try {
                            AuthRepository authRepo = createAuthRepo();
                            retrofit2.Response<TokenResponse> refreshResponse = authRepo
                                    .refreshtoken(refreshToken)
                                    .execute();

                            if (refreshResponse.isSuccessful() && refreshResponse.body() != null) {
                                String newAccessToken = refreshResponse.body().getAccess();
                                sessionManager.saveToken(newAccessToken);
                            } else {
                                sessionManager.clear();
                                throw new IOException("Refresh token inválido");
                            }
                        } catch (Exception e) {
                            sessionManager.clear();
                            throw new IOException("Erro ao renovar token: " + e.getMessage(), e);
                        }
                    } else {
                        throw new IOException("Sem refresh token disponível");
                    }
                }
            }
        }

        String token = sessionManager.getToken();
        if (token != null && !token.isEmpty()) {
            Request newRequest = originalRequest.newBuilder()
                    .addHeader("Authorization", "Bearer " + token)
                    .build();
            return chain.proceed(newRequest);
        }

        return chain.proceed(originalRequest);
    }

    private AuthRepository createAuthRepo() {
        OkHttpClient client = new OkHttpClient.Builder().build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8000/pt-br/api/v1/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(AuthRepository.class);
    }
}
