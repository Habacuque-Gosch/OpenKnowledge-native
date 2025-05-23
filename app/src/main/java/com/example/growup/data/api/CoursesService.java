// CoursesService.java
package com.example.growup.data.api;

import com.example.growup.data.model.CourseResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CoursesService {
    @GET("courses/")
    Call<CourseResponse> getCourses();
}
