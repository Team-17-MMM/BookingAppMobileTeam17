package com.example.bookingappteam17.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bookingappteam17.databinding.ActivityAccommodationsDetailsBinding;
import com.example.bookingappteam17.model.Accommodation;

public class AccommodationDetailActivity extends AppCompatActivity {
    private boolean isPermissions = true;
    private String [] permissions = {
            android.Manifest.permission.INTERNET
    };
    private static final int REQUEST_PERMISSIONS = 200;
    private ActivityAccommodationsDetailsBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAccommodationsDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Accommodation selectedAccommodation = getIntent().getParcelableExtra("selected_accommodation");

        if (selectedAccommodation != null) {
            binding.accommodationDetailsTitle.setText(selectedAccommodation.getName());
            binding.accommodationDetailsDescription.setText(selectedAccommodation.getDescription());
//            binding.accommodationDetailsImage.setImageResource(selectedAccommodation.getImage());

            binding.accommodationDetailsPrice.setText(String.valueOf(selectedAccommodation.getPrice()));

        }
    }
}
