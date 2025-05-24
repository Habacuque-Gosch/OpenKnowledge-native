package com.example.growup.ui.auth;
import com.example.growup.data.api.RegisterResponse;
import  com.example.growup.utils.SessionManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.growup.R;
import com.example.growup.data.api.LoginResponse;
import com.example.growup.data.repository.AuthRepository;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private EditText nameInput, emailInput, passwordInput;
    private Button registerButton;
    private TextView registerText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        nameInput = findViewById(R.id.nameInput);
        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
        registerButton = findViewById(R.id.registerButton);
        registerText = findViewById(R.id.registerText);

        registerButton.setOnClickListener(v -> register());
        registerText.setOnClickListener(v -> {

            finish();
            startActivity(new Intent(this, LoginActivity.class));

        });
    }

    private void register() {
        String name = nameInput.getText().toString().trim();
        String email = emailInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();

        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
            return;
        }

        AuthRepository repo = new AuthRepository(this);
        repo.register(name, email, password).enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(RegisterActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                    finish();
                } else {

                    try {
                        String errormessage = response.errorBody().string();
                        Log.e("RegisterError", "Erro ao registrar: " + errormessage);
                        Toast.makeText(RegisterActivity.this, "Erro ao registrar: " + errormessage, Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        Toast.makeText(RegisterActivity.this, "Erro ao processar o erro", Toast.LENGTH_SHORT).show();
                    }

                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "Erro de rede", Toast.LENGTH_SHORT).show();
            }
        });
    }

}

