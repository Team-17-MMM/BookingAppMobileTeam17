package com.example.bookingappteam17.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.bookingappteam17.R;
import com.example.bookingappteam17.clients.ClientUtils;
import com.example.bookingappteam17.databinding.ActivityHomeBinding;
import com.example.bookingappteam17.dto.UserInfoDTO;
import com.example.bookingappteam17.fragments.HomeFragment;
import com.example.bookingappteam17.fragments.NotificationPageFragment;
import com.example.bookingappteam17.fragments.NotificationsListFragment;
import com.example.bookingappteam17.fragments.ProfileFragment;
import com.example.bookingappteam17.fragments.ReservationsListFragment;
import com.example.bookingappteam17.fragments.resorts.ResortPageFragment;
import com.example.bookingappteam17.model.Resort;
import com.example.bookingappteam17.viewmodel.SharedViewModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {
    private boolean isPermissions = true;
    private String [] permissions = {
            android.Manifest.permission.INTERNET
    };
    private static final int REQUEST_PERMISSIONS = 200;
    private ActivityHomeBinding binding;

    private SharedViewModel sharedViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        loadUserData();
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sharedViewModel = new ViewModelProvider(this).get(SharedViewModel.class);

        replaceFragment(new ResortPageFragment());
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.home) {
                replaceFragment(new ResortPageFragment());
            } else if (item.getItemId() == R.id.notifications) {
                replaceFragment(new NotificationPageFragment());
            } else if (item.getItemId() == R.id.profile) {
                replaceFragment(new ProfileFragment());
            } else if (item.getItemId() == R.id.reservations) {
                replaceFragment(new ReservationsListFragment());
            }
            return true;
        });
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.home_frame_layout, fragment);
        fragmentTransaction.commit();
    }

    // Load user data from the server
    private void loadUserData() {
        SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE);

        String username = sharedPreferences.getString("username", "");

        Call<UserInfoDTO> call = ClientUtils.userService.getUserInfo(username);
        call.enqueue(new Callback<UserInfoDTO>() {
            @Override
            public void onResponse(Call<UserInfoDTO> call, Response<UserInfoDTO> response) {
                if (response.isSuccessful() && response.body() != null) {
                    UserInfoDTO userInfoDTO = response.body();
                    sharedViewModel.setUserInfoDTO(userInfoDTO);
                } else {
                    Log.d("Error", "Failed to retrieve user data");
                }
            }

            @Override
            public void onFailure(Call<UserInfoDTO> call, Throwable t) {
                Log.e("Error", "Network request failed", t);
            }
        });
    }
}
