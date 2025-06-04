package com.example.growup.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.growup.data.model.profile.Profile;
import com.example.growup.data.repository.ProfileRespository;
import com.example.growup.utils.SessionManager;

public class ProfileViewModel extends ViewModel {

    private MutableLiveData<Profile> profileLiveData = new MutableLiveData<>();
    private MutableLiveData<String> errorLiveData = new MutableLiveData<>();
    private ProfileRespository repository = new ProfileRespository();

    public LiveData<Profile> getProfile() {
        return profileLiveData;
    }

    public LiveData<String> getError() {
        return errorLiveData;
    }

    public void fetchProfile(Context context) {
        repository.getProfile(context, new ProfileRespository.ProfileCallback() {
            @Override
            public void onSuccess(Profile profile) {
                profileLiveData.postValue(profile);
                new SessionManager(context).saveProfile(profile);;
            }

            @Override
            public void onError(String errorMessage) {
                errorLiveData.postValue(errorMessage);
            }
        });
    }

    public void loadProfileFromPrefs(Context context) {
        Profile savedProfile = new SessionManager(context).getProfile();
        if (savedProfile != null) {
            profileLiveData.setValue(savedProfile);
        }
    }

    public void updateProfile(Profile profile, Context context) {
        profileLiveData.setValue(profile);
        new SessionManager(context).saveProfile(profile);
    }
}
