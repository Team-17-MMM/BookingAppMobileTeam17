package com.example.bookingappteam17.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.bookingappteam17.R;
import com.example.bookingappteam17.activities.EditProfileActivity;
import com.example.bookingappteam17.activities.LoginActivity;
import com.example.bookingappteam17.clients.ClientUtils;
import com.example.bookingappteam17.dto.UserInfoDTO;
import com.example.bookingappteam17.viewmodel.SharedViewModel;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {

    private SharedViewModel sharedViewModel;
    private UserInfoDTO userInfoDTO;

    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        ImageView profileEditIcon = view.findViewById(R.id.profileEditIcon);
        profileEditIcon.setOnClickListener(v -> editIconClicked());

        Button buttonLogout = view.findViewById(R.id.btnLogout);
        buttonLogout.setOnClickListener(v -> logout());

        return view;
    }

    // Open edit profile activity
    private void editIconClicked() {
        Intent intent = new Intent(getActivity(), EditProfileActivity.class);
        UserInfoDTO userInfoDTO = sharedViewModel.getUserInfoDTO();
        intent.putExtra("userInfoDTO", (Parcelable) userInfoDTO);
        startActivity(intent);
    }

    // Logout and navigate to login activity
    private void logout() {
        // Clear token and username from SharedPreferences
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("token");
        editor.remove("username");
        editor.apply();

        // Clear UserInfoDTO from SharedViewModel
        sharedViewModel.setUserInfoDTO(null);

        // Navigate to login activity
        Intent intent = new Intent(requireActivity(), LoginActivity.class);
        startActivity(intent);
        requireActivity().finish(); // Optional: Close the current activity
    }

    // Set up user data in the UI
    private void setupData(View view) {

        TextView textViewUsername = view.findViewById(R.id.txtName);
        textViewUsername.setText(userInfoDTO.getName());

        TextView textViewName = view.findViewById(R.id.txtLastName);
        textViewName.setText(userInfoDTO.getLastname());

        TextView textViewEmail = view.findViewById(R.id.txtEmail);
        textViewEmail.setText(userInfoDTO.getUsername());

        TextView textViewPhone = view.findViewById(R.id.txtPhoneNumber);
        textViewPhone.setText(userInfoDTO.getPhone());

        TextView textViewAddress = view.findViewById(R.id.txtAddress);
        textViewAddress.setText(userInfoDTO.getAddress());
    }

    @Override
    public void onResume() {
        super.onResume();
        loadUser();
    }

    // Load user data from the server
    private void loadUser() {
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");

        Call<UserInfoDTO> call = ClientUtils.userService.getUserInfo(username);
        call.enqueue(new Callback<UserInfoDTO>() {
            @Override
            public void onResponse(Call<UserInfoDTO> call, Response<UserInfoDTO> response) {
                if (response.isSuccessful() && response.body() != null) {
                    userInfoDTO = response.body();
                    sharedViewModel.setUserInfoDTO(userInfoDTO);
                    setupData(requireView());
                } else {
                    Log.d("Error", "Failed to retrieve user data");
                }
            }

            @Override
            public void onFailure(Call<UserInfoDTO> call, Throwable t) {
                Log.d("Error", Objects.requireNonNull(t.getLocalizedMessage()));
            }
        });
    }
}
