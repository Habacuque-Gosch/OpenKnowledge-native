package com.example.growup.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
//import androidx.lifecycle.ViewModel;

import com.example.growup.data.api.LoginResponse;
import com.example.growup.data.api.RegisterRequest;
import com.example.growup.data.repository.AuthRepository;
import com.example.growup.utils.SessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthViewModel extends ViewModel {
    private final MutableLiveData<Boolean> loginSuccess = new MutableLiveData<>();
    private final MutableLiveData<Boolean> registerSuccess = new MutableLiveData<>();


    public LiveData<Boolean> getLoginSuccess() {
        return loginSuccess;
    }

    public LiveData<Boolean> getRegisterSuccess() {
        return registerSuccess;
    }

    public void login(Context context, String email, String password) {
        AuthRepository repo = new AuthRepository(context);
        repo.login(email, password).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    new SessionManager(context).saveToken(response.body().getAccess());
                    loginSuccess.setValue(true);
                } else {
                    loginSuccess.setValue(false);
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                loginSuccess.setValue(false);
            }
        });
    }
    public void register(Context context, String name, String email, String password) {
        AuthRepository repo = new AuthRepository(context);
        repo.register(name, email, password).enqueue(new Callback<RegisterRequest>() {
            @Override
            public void onResponse(Call<RegisterRequest> call, Response<RegisterRequest> response) {
                if (response.isSuccessful() && response.body() != null) {
                    new SessionManager(context).saveToken(response.body().getAccess());
                    registerSuccess.setValue(true);
                } else {
                    registerSuccess.setValue(false);
                }
            }

            @Override
            public void onFailure(Call<RegisterRequest> call, Throwable t) {
                registerSuccess.setValue(false);
            }
        });
    };
}
