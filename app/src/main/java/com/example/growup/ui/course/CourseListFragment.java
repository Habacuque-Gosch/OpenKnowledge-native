package com.example.growup.ui.course;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.growup.R;
import com.example.growup.data.model.course.Course;
import com.example.growup.ui.course.adapter.CourseAdapter;
import com.example.growup.viewmodel.CourseViewModel;

import java.util.ArrayList;
import java.util.List;

public class CourseListFragment extends Fragment {

    private RecyclerView recyclerCourses;
    private CourseAdapter courseAdapter;
    private List<Course> courseList = new ArrayList<>();
    private CourseViewModel viewModel;

    public CourseListFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.course_fragment_list, container, false);
        recyclerCourses = view.findViewById(R.id.recyclerCourses);
        recyclerCourses.setLayoutManager(new LinearLayoutManager(requireContext()));

        courseAdapter = new CourseAdapter(courseList);
        recyclerCourses.setAdapter(courseAdapter);

        viewModel = new ViewModelProvider(this).get(CourseViewModel.class);

        observeData();
        viewModel.fetchCourses(requireContext());

        return view;
    }

    private void observeData() {
        viewModel.getCourses().observe(getViewLifecycleOwner(), courses -> {
            if (courses != null) {
                courseList.clear();
                courseList.addAll(courses);
                courseAdapter.notifyDataSetChanged();
            } else {
                Toast.makeText(requireContext(), "Erro ao carregar cursos", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
