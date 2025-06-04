package com.example.growup.data.api.profile;

import com.example.growup.data.model.profile.Profile;
import com.example.growup.data.model.profile.ProfileRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;

public interface ProfileService {

    @GET("profiles/me/")
    Call<Profile> getProfile();

    @PATCH("profiles/me/")
    Call<Profile> updateProfile(@Body ProfileRequest profile);

//    @GET
//    Call<Destination> getSavedDestinations();

//    @GET
//    Call<Profile> getProfile();

}
