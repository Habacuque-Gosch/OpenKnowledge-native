package com.example.growup.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.growup.R;
import com.example.growup.data.api.TokenResponse;
import com.example.growup.data.repository.AuthRepository;
import com.example.growup.ui.course.IndexCoursesActivity;
import com.example.growup.utils.SessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SessionManager sessionManager = new SessionManager(this);
        String refreshToken = sessionManager.getRefreshToken();

        if (refreshToken != null) {
            AuthRepository repo = new AuthRepository(this);
            Log.d("REFRESH_TOKEN", "AREQ: ");

            repo.refreshtoken(refreshToken).enqueue(new Callback<TokenResponse>() {

                @Override
                public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {
                    Log.d("REFRESH_TOKEN", "REQ: ");

                    if (response.isSuccessful()) {
                        String newAccessToken = response.body().getAccess();
                        sessionManager.saveToken(newAccessToken);

                        startActivity(new Intent(MainActivity.this, IndexCoursesActivity.class));
                        finish();
                    } else {
                        sessionManager.clear();
                    }
                }

                @Override
                public void onFailure(Call<TokenResponse> call, Throwable t) {
                    Toast.makeText(MainActivity.this, "Erro de conexão", Toast.LENGTH_SHORT).show();
                }
            });
        }

        Button btnLogin = findViewById(R.id.btnLogin);
        Button btnRegister = findViewById(R.id.btnRegister);

        btnLogin.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        });

        btnRegister.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }
}
