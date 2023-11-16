package com.example.bookingappteam17.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bookingappteam17.databinding.ActivityHomeBinding;
import com.example.bookingappteam17.databinding.ActivityResortDetailsBinding;

public class ResortDetailActivity extends AppCompatActivity {
    private boolean isPermissions = true;
    private String [] permissions = {
            android.Manifest.permission.INTERNET
    };
    private static final int REQUEST_PERMISSIONS = 200;
    private ActivityResortDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityResortDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}
