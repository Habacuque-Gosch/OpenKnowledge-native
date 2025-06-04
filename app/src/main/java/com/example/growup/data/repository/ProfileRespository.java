package com.example.growup.data.repository;

import android.content.Context;

import com.example.growup.data.api.ApiClient;
import com.example.growup.data.api.profile.ProfileService;
import com.example.growup.data.model.profile.Profile;
import com.example.growup.data.model.profile.ProfileRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileRespository {

    public interface ProfileCallback {
        void onSuccess (Profile profile);
        void onError(String errorMessage);
    }

    public void getProfile(Context context, ProfileCallback callback) {
        ProfileService service = ApiClient.getClient(context).create(ProfileService.class);
        service.getProfile().enqueue(new Callback<Profile>() {
            public void onResponse(Call<Profile> call, Response<Profile> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Erro ao buscar perfil: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Profile> call, Throwable t) {
                callback.onError("Erro de conexão: " + t.getMessage());
            }
        });
    }

    public void updateProfile(Context context, String name, String bio, int age, ProfileCallback callback) {
        ProfileRequest profileRequest = new ProfileRequest(name, bio, age);

        ProfileService service = ApiClient.getClient(context).create(ProfileService.class);
        service.updateProfile(profileRequest).enqueue(new Callback<Profile>() {
            @Override
            public void onResponse(Call<Profile> call, Response<Profile> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Erro ao editar perfil: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Profile> call, Throwable t) {
                callback.onError("Erro de conexão: " + t.getMessage());
            }
        });
    }


}
