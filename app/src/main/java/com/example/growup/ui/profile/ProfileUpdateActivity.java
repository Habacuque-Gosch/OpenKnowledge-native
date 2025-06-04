package com.example.growup.ui.profile;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.growup.R;
import com.example.growup.data.model.profile.Profile;
import com.example.growup.data.repository.ProfileRespository;
import com.example.growup.viewmodel.ProfileViewModel;

public class ProfileUpdateActivity extends AppCompatActivity {

    private EditText full_name_input, bio_input, age_input;
    private Button updateProfileButton;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_update_profile);

        full_name_input = findViewById(R.id.nameInput);
        bio_input = findViewById(R.id.bioInput);
        age_input  = findViewById(R.id.ageInput);
        updateProfileButton = findViewById(R.id.updateProfileButton);

        ProfileViewModel profileViewModel = new ViewModelProvider(
                ProfileUpdateActivity.this,
                new ViewModelProvider.AndroidViewModelFactory(getApplication())
        ).get(ProfileViewModel.class);

        profileViewModel.fetchProfile(ProfileUpdateActivity.this);
        profileViewModel.getProfile().observe(ProfileUpdateActivity.this, profile -> {
            if (profile != null) {
                full_name_input.setText(profile.getName());
                bio_input.setText(profile.getBio());
                age_input.setText(String.valueOf(profile.getAge()));
            }
        });

        updateProfileButton.setOnClickListener( v -> updateProfile());

    }

    private void updateProfile() {
        String name = full_name_input.getText().toString().trim();
        String bio = bio_input.getText().toString();
        String ageStr = age_input.getText().toString().trim();

        if (name.isEmpty() || bio.isEmpty() || ageStr.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
            return;
        }

        int age;
        try {
            age = Integer.parseInt(ageStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Idade inválida", Toast.LENGTH_SHORT).show();
            return;
        }

        ProfileRespository repo = new ProfileRespository();
        repo.updateProfile(this, name, bio, age, new ProfileRespository.ProfileCallback() {
            @Override
            public void onSuccess(Profile profile) {
                Toast.makeText(getApplicationContext(), "Perfil atualizado!", Toast.LENGTH_SHORT).show();
                finish();
            }
            @Override
            public void onError(String errorMessage) {
                Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show();
            }
        });

    }

}
