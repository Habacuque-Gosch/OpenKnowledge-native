package com.example.growup.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.growup.ui.profile.ProfileUpdateActivity;
import com.example.growup.R;
import com.example.growup.ui.auth.MainActivity;
import com.example.growup.utils.SessionManager;
import com.example.growup.viewmodel.ProfileViewModel;

public class ProfileFragment extends Fragment {

    private ProfileViewModel viewModel;
    private TextView tvName, tvAge, tvBio, tvLogout, tvUpdateProfile;
    private ImageView ivPhoto;

    public ProfileFragment() {}

    private SessionManager sessionManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_fragment, container, false);

        tvName = view.findViewById(R.id.tvName);
        tvAge = view.findViewById(R.id.tvAge);
        tvBio = view.findViewById(R.id.tvBio);
        ivPhoto = view.findViewById(R.id.ivPhoto);
        tvLogout = view.findViewById(R.id.tvLogout);

        tvUpdateProfile = view.findViewById(R.id.tvUpdateProfile);

        tvUpdateProfile.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), ProfileUpdateActivity.class);
            startActivity(intent);
        });

        sessionManager = new SessionManager(getContext());
        tvLogout.setOnClickListener( v -> {
            sessionManager.clear();

            Intent intent = new Intent(requireContext(), MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

        });

        viewModel = new ViewModelProvider(this).get(ProfileViewModel.class);

        viewModel.loadProfileFromPrefs(requireContext());

        viewModel.getProfile().observe(getViewLifecycleOwner(), profile -> {
            if (profile != null) {

                tvName.setText(profile.getName());
                tvAge.setText(String.valueOf(profile.getAge()));
                tvBio.setText(profile.getBio());
                Log.d("profile_img", profile.getPhoto());
                String fullImageUrl = profile.getPhoto();
                Glide.with(this)
                        .load(fullImageUrl)
                        .circleCrop()
                        .into(ivPhoto);
            }
        });

        viewModel.getError().observe(getViewLifecycleOwner(), error -> {
            if (error != null) {
                Toast.makeText(getContext(), "Error ao carregar perfil", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}

