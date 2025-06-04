package com.example.growup.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.growup.R;
import com.example.growup.data.api.ApiClient;
import com.example.growup.data.api.auth.TokenResponse;
import com.example.growup.data.repository.AuthRepository;
import com.example.growup.ui.course.IndexCoursesActivity;
import com.example.growup.utils.SessionManager;
import com.example.growup.viewmodel.ProfileViewModel;
//import com.example.growup.utils.UpdateApk;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        new UpdateApk(this).checkForUpdate();
//
        SessionManager sessionManager = new SessionManager(this);
        String refreshToken = sessionManager.getRefreshToken();

        if (refreshToken != null) {
            AuthRepository repo = new AuthRepository(this);

            repo.refreshtoken(refreshToken).enqueue(new Callback<TokenResponse>() {

                @Override
                public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {

                    if (response.isSuccessful()) {
                        String newAccessToken = response.body().getAccess();
                        sessionManager.saveToken(newAccessToken);
                        ApiClient.reset();

                        ProfileViewModel profileViewModel = new ViewModelProvider(
                                MainActivity.this,
                                new ViewModelProvider.AndroidViewModelFactory(getApplication())
                        ).get(ProfileViewModel.class);

                        profileViewModel.fetchProfile(MainActivity.this);

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
