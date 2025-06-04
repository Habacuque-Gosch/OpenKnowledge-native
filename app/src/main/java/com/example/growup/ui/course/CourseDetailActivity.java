package com.example.growup.ui.course;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.growup.R;
import com.example.growup.data.api.ApiClient;
import com.example.growup.data.model.course.Course;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CourseDetailActivity extends AppCompatActivity {

    private TextView nameText, contentText;

    public static void start(Context context, int destinationId) {
        Intent intent = new Intent(context, CourseDetailActivity.class);
        intent.putExtra("destination_id", destinationId);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);

        nameText = findViewById(R.id.destinationName);
        contentText = findViewById(R.id.detinationDescription);

        int destinationId = getIntent().getIntExtra("destination_id", -1);

        if (destinationId != -1) {
            ApiClient.getCoursesService(this).getCourse(destinationId)
                    .enqueue(new Callback<Course>() {
                        @Override
                        public void onResponse(Call<Course> call, Response<Course> response) {
                            if (response.isSuccessful() && response.body() != null) {
                                Course course = response.body();
                                nameText.setText(course.getTitle());
                                contentText.setText(course.getDescription());
                            } else {
                                nameText.setText("Erro ao carregar destino");
                            }
                        }

                        @Override
                        public void onFailure(Call<Course> call, Throwable t) {
                            nameText.setText("Erro de conexão");
                            contentText.setText(t.getMessage());
                        }
                    });
        }
    }
}
