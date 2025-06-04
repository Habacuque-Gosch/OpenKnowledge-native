// CoursesService.java
package com.example.growup.data.api.course;

import com.example.growup.data.model.course.Course;
import com.example.growup.data.model.course.CourseResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CoursesService {
    @GET("courses/")
    Call<CourseResponse> getCourses();

    @GET("destinations/{id}/")
    Call<Course> getCourse(@Path("id") int id);
}
