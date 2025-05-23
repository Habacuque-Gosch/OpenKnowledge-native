
package com.example.growup.ui.course;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.growup.R;

public class IndexCoursesActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index_courses);

        if (savedInstanceState == null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new CourseListFragment())
                    .commit();
        }

    }

}
