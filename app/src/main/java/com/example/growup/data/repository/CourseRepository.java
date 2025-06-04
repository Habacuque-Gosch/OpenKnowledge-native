package com.example.growup.data.repository;

import android.content.Context;

import com.example.growup.data.api.ApiClient;
import com.example.growup.data.api.course.CoursesService;
import com.example.growup.data.model.course.Course;
import com.example.growup.data.model.course.CourseResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CourseRepository {
    public interface CourseCallback {
        void onSuccess(List<Course> courseList);
        void onError(String errorMessage);
    }

    public void getCourses(Context context, CourseCallback callback) {
        CoursesService service = ApiClient.getClient(context).create(CoursesService.class);
        service.getCourses().enqueue(new Callback<CourseResponse>() {
            @Override
            public void onResponse(Call<CourseResponse> call, Response<CourseResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body().getResults());
                } else {
                    callback.onError("Erro ao buscar cursos");
                }
            }

            @Override
            public void onFailure(Call<CourseResponse> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }
}
