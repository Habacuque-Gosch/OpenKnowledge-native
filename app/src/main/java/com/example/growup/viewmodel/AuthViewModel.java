package com.example.growup.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
//import androidx.lifecycle.ViewModel;

import com.example.growup.data.api.LoginResponse;
import com.example.growup.data.repository.AuthRepository;
import com.example.growup.utils.SessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthViewModel extends ViewModel {
    private final MutableLiveData<Boolean> loginSuccess = new MutableLiveData<>();

    public LiveData<Boolean> getLoginSuccess() {
        return loginSuccess;
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
}
