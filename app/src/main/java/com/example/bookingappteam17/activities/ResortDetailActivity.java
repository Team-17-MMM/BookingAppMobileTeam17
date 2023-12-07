package com.example.bookingappteam17.activities;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bookingappteam17.R;
import com.example.bookingappteam17.databinding.ActivityHomeBinding;
import com.example.bookingappteam17.databinding.ActivityResortDetailsBinding;
import com.example.bookingappteam17.model.Resort;

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
        Resort selectedResort = getIntent().getParcelableExtra("selected_resort");

        if (selectedResort != null) {
            binding.resortDetailsTitle.setText(selectedResort.getName());
            binding.resortDetailsDescription.setText(selectedResort.getDescription());
            binding.resortDetailsImage.setImageResource(selectedResort.getImage());
            binding.resortDetailsPrice.setText(String.valueOf(selectedResort.getPrice()));

        }
    }
}
