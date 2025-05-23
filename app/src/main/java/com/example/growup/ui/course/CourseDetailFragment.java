package com.example.growup.ui.course;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.growup.R;
import com.example.growup.data.model.Course;

public class CourseDetailFragment extends Fragment {
    private TextView titleText, contentText, dateText;

    public CourseDetailFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.course_fragment_detail, container, false);
        titleText = view.findViewById(R.id.courseTitle);
        contentText = view.findViewById(R.id.courseContent);
        dateText = view.findViewById(R.id.courseDate);

        if (getArguments() != null) {
            Course course = (Course) getArguments().getSerializable("course");
            if (course != null) {
                titleText.setText(course.getTitle());
                contentText.setText(course.getDescription());
                dateText.setText(course.getCreation());
            }
        }

        return view;
    }
}
