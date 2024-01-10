package com.example.bookingappteam17.activities.accommodation;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bookingappteam17.databinding.ActivityRateHostBinding;

public class RateHostActivity extends AppCompatActivity {
    ActivityRateHostBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRateHostBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

}
