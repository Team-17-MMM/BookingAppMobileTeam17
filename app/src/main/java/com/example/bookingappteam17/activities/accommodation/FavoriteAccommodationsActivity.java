package com.example.bookingappteam17.activities.accommodation;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.bookingappteam17.R;
import com.example.bookingappteam17.clients.ClientUtils;
import com.example.bookingappteam17.databinding.ActivityFavoriteAccommodationsBinding;
import com.example.bookingappteam17.databinding.ActivityHomeBinding;
import com.example.bookingappteam17.dto.user.UserInfoDTO;
import com.example.bookingappteam17.fragments.accommodation.AccommodationPageFragment;
import com.example.bookingappteam17.fragments.accommodation.FavoriteAccommodationFragment;
import com.example.bookingappteam17.viewmodel.SharedViewModel;
import com.google.android.material.datepicker.MaterialDatePicker;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavoriteAccommodationsActivity  extends AppCompatActivity {

    private static final String USER_PREFS_KEY = "user_prefs";
    private static final String USERNAME_KEY = "username";
    private static final String ROLE_KEY = "role";
    private static final String USER_ID_KEY = "userId";
    private ActivityFavoriteAccommodationsBinding binding;
    private SharedViewModel sharedViewModel;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPreferences = getSharedPreferences(USER_PREFS_KEY, Context.MODE_PRIVATE);
        sharedViewModel = new ViewModelProvider(this).get(SharedViewModel.class);
        loadUserData();
        super.onCreate(savedInstanceState);
        binding = ActivityFavoriteAccommodationsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new FavoriteAccommodationFragment());
    }


    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.home_frame_layout, fragment);
        fragmentTransaction.commit();
    }

    private void loadUserData() {
        String username = sharedPreferences.getString(USERNAME_KEY, "");

        Call<UserInfoDTO> call = ClientUtils.userService.getUserInfo(username);
        call.enqueue(new Callback<UserInfoDTO>() {
            @Override
            public void onResponse(Call<UserInfoDTO> call, Response<UserInfoDTO> response) {
                if (response.isSuccessful() && response.body() != null) {
                    UserInfoDTO userInfoDTO = response.body();
                    // add userId to shared preferences
                    sharedPreferences.edit().putLong("userId", userInfoDTO.getUserID()).apply();
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
