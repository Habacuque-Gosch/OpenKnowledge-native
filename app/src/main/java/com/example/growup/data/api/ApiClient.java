package com.example.growup.data.api;

import android.content.Context;

import com.example.growup.data.api.auth.AuthInterceptor;
import com.example.growup.data.api.course.CoursesService;
import com.example.growup.data.api.profile.ProfileService;
import com.example.growup.utils.SessionManager;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static Retrofit retrofit;

    public static Retrofit getClient(Context context) {
        if (retrofit == null) {
            SessionManager sessionManager = new SessionManager(context);
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(new AuthInterceptor(sessionManager))
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl("http://10.0.2.2:8000/pt-br/api/v1/")
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        }
        return retrofit;
    }

    public static void reset() {
        retrofit = null;
    }

    public static CoursesService getCoursesService(Context context) {
        return getClient(context).create(CoursesService.class);
    }

    public static ProfileService getProfileService(Context context) {
        return getClient(context).create(ProfileService.class);
    }

}
