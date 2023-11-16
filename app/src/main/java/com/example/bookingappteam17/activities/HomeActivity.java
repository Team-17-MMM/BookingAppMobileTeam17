package com.example.bookingappteam17.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.bookingappteam17.R;
import com.example.bookingappteam17.databinding.ActivityHomeBinding;
import com.example.bookingappteam17.fragments.HomeFragment;
import com.example.bookingappteam17.fragments.NotificationPageFragment;
import com.example.bookingappteam17.fragments.NotificationsListFragment;
import com.example.bookingappteam17.fragments.ProfileFragment;
import com.example.bookingappteam17.fragments.ReservationsListFragment;
import com.example.bookingappteam17.fragments.resorts.ResortPageFragment;
import com.example.bookingappteam17.model.Resort;

public class HomeActivity extends AppCompatActivity {
    private boolean isPermissions = true;
    private String [] permissions = {
            android.Manifest.permission.INTERNET
    };
    private static final int REQUEST_PERMISSIONS = 200;
    private ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
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
}
