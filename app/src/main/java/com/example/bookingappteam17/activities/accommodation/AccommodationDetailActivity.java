package com.example.bookingappteam17.activities.accommodation;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bookingappteam17.databinding.ActivityAccommodationsDetailsBinding;
import com.example.bookingappteam17.dto.accommodation.AccommodationDTO;
import com.example.bookingappteam17.fragments.reservation.ReservationFragment;
import com.example.bookingappteam17.viewmodel.SharedViewModel;

public class AccommodationDetailActivity extends AppCompatActivity {
    private boolean isPermissions = true;
    private String [] permissions = {
            android.Manifest.permission.INTERNET
    };

    private SharedViewModel sharedViewModel;

    private static final int REQUEST_PERMISSIONS = 200;
    private ActivityAccommodationsDetailsBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAccommodationsDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        AccommodationDTO selectedAccommodation = getIntent().getParcelableExtra("selected_accommodation");

        Intent intent = getIntent();
        if (intent != null) {
            sharedViewModel = intent.getParcelableExtra("sharedViewModel");
        }

        if (selectedAccommodation != null) {
            binding.accommodationDetailsTitle.setText(selectedAccommodation.getName());
            binding.accommodationDetailsDescription.setText(selectedAccommodation.getDescription());
//            binding.accommodationDetailsImage.setImageResource(selectedAccommodation.getImage());

            binding.accommodationDetailsPrice.setText(String.valueOf(selectedAccommodation.getPrice()));
            Button btnReservation = binding.accommodationDetailsReservationButton;
            btnReservation.setOnClickListener(v -> {
                ReservationFragment reservationFragment = new ReservationFragment(selectedAccommodation, sharedViewModel);
                reservationFragment.show(getSupportFragmentManager(), "ReservationFragmentTag");
            });


        }
    }
}
