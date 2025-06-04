package com.example.growup.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.growup.R;
import com.example.growup.data.api.ApiClient;
import com.example.growup.data.api.auth.LoginResponse;
import com.example.growup.data.repository.AuthRepository;
import com.example.growup.ui.course.IndexCoursesActivity;
import com.example.growup.utils.SessionManager;
import com.example.growup.viewmodel.ProfileViewModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity {

    private EditText emailInput, passwordInput;
    private Button loginButton;
    private TextView registerText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
        loginButton = findViewById(R.id.loginButton);
        registerText = findViewById(R.id.registerText);

        loginButton.setOnClickListener(v -> login(

        ));
        registerText.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }

    private void login() {
        String email = emailInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
            return;
        }

        AuthRepository repo = new AuthRepository(this);
        repo.login(email, password).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    SessionManager session = new SessionManager(LoginActivity.this);

                    session.saveToken(response.body().getAccess());
                    session.saveRefreshToken(response.body().getRefresh());
                    ApiClient.reset();

                    ProfileViewModel profileViewModel = new ViewModelProvider(
                            LoginActivity.this,
                            new ViewModelProvider.AndroidViewModelFactory(getApplication())
                    ).get(ProfileViewModel.class);

                    profileViewModel.fetchProfile(LoginActivity.this);
                    profileViewModel.getProfile().observe(LoginActivity.this, profile -> {
                        if (profile != null) {
                            Toast.makeText(LoginActivity.this, "Login concluído!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, IndexCoursesActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });

                } else {

                    if (response.code() == 401 || response.code() == 400) {
                        Toast.makeText(LoginActivity.this, "E-mail ou senha inválidos", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LoginActivity.this, "Erro inesperado. Tente novamente.", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Erro de rede", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

