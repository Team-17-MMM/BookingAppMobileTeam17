package com.example.bookingappteam17.activities.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.bookingappteam17.R;
import com.example.bookingappteam17.clients.ClientUtils;
import com.example.bookingappteam17.databinding.ActivityHomeBinding;
import com.example.bookingappteam17.dto.user.UserInfoDTO;
import com.example.bookingappteam17.enums.user.UserRoleType;
import com.example.bookingappteam17.fragments.accommodation.AccommodationPageViewModel;
import com.example.bookingappteam17.fragments.notification.NotificationPageFragment;
import com.example.bookingappteam17.fragments.account.ProfileFragment;
import com.example.bookingappteam17.fragments.reservation.ReservationPageFragment;
import com.example.bookingappteam17.fragments.reservation.ReservationsListFragment;
import com.example.bookingappteam17.fragments.accommodation.AccommodationPageFragment;
import com.example.bookingappteam17.fragments.accommodation.ApproveAccommodationPageFragment;
import com.example.bookingappteam17.viewmodel.SharedViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {

    private static final String USER_PREFS_KEY = "user_prefs";
    private static final String USERNAME_KEY = "username";
    private static final String ROLE_KEY = "role";
    private static final String USER_ID_KEY = "userId";
    private ActivityHomeBinding binding;
    private SharedViewModel sharedViewModel;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPreferences = getSharedPreferences(USER_PREFS_KEY, Context.MODE_PRIVATE);
        sharedViewModel = new ViewModelProvider(this).get(SharedViewModel.class);
        loadUserData();
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setupBottomNavigationView();
        replaceFragment(new AccommodationPageFragment());
    }

    private void setupBottomNavigationView() {
        binding.bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            handleBottomNavigationItemSelected(item.getItemId());
            return true;
        });
    }

    private void handleBottomNavigationItemSelected(int itemId) {
        if (itemId == R.id.profile) {
            replaceFragment(new ProfileFragment());
        } else if (itemId == R.id.notifications) {
            replaceFragment(new NotificationPageFragment());
        } else if (itemId == R.id.reservations) {
            replaceFragment(new ReservationPageFragment());
        } else if (itemId == R.id.accommodations) {
            replaceFragment(new AccommodationPageFragment());
        } else if (itemId == R.id.approve) {
            replaceFragment(new ApproveAccommodationPageFragment());
        }
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
                    updateMenuVisibility();
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

    private void updateMenuVisibility() {
        BottomNavigationView bottomNavigationView = binding.bottomNavigationView;
        Menu menu = bottomNavigationView.getMenu();

        menu.findItem(R.id.profile).setVisible(true);
        menu.findItem(R.id.notifications).setVisible(true);

        String role = sharedPreferences.getString(ROLE_KEY, "");
        switch (UserRoleType.valueOf(role)) {
            case GUEST:
                menu.findItem(R.id.reservations).setVisible(true);
                menu.findItem(R.id.accommodations).setVisible(true);
                menu.findItem(R.id.approve).setVisible(false);
                break;
            case HOST:
                menu.findItem(R.id.reservations).setVisible(true);
                menu.findItem(R.id.accommodations).setVisible(true);
                menu.findItem(R.id.approve).setVisible(false);
                break;
            case ADMIN:
                menu.findItem(R.id.reservations).setVisible(false);
                menu.findItem(R.id.accommodations).setVisible(true);
                menu.findItem(R.id.approve).setVisible(true);
                break;
        }
    }

    public SharedViewModel getSharedViewModel() {
        return sharedViewModel;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

}
