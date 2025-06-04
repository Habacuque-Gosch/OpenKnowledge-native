package com.example.growup.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.growup.data.model.course.Course;
import com.example.growup.data.repository.CourseRepository;

import java.util.List;

public class CourseViewModel extends ViewModel {

    private final MutableLiveData<List<Course>> coursesLiveData = new MutableLiveData<>();
    private final CourseRepository repository = new CourseRepository();

    public LiveData<List<Course>> getCourses() {
        return coursesLiveData;
    }

    public void fetchCourses(Context context) {
        repository.getCourses(context, new CourseRepository.CourseCallback() {
            @Override
            public void onSuccess(List<Course> courseList) {
                coursesLiveData.postValue(courseList);
            }

            @Override
            public void onError(String errorMessage) {
                // Você pode usar um LiveData<String> separado para mensagens de erro, se quiser
            }
        });
    }
}

